package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.protobuf.InvalidProtocolBufferException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.grpc.ManagedChannel;
import io.reactivex.Scheduler;
import io.reactivex.Completable;
import iroha.protocol.BlockOuterClass;
import iroha.protocol.CommandServiceGrpc;
import jp.co.soramitsu.iroha.android.Keypair;
import jp.co.soramitsu.iroha.android.ByteVector;
import jp.co.soramitsu.iroha.android.UnsignedTx;
import jp.co.soramitsu.iroha.android.ModelProtoTransaction;
import jp.co.soramitsu.iroha.android.sample.PreferencesUtil;
import jp.co.soramitsu.iroha.android.ModelTransactionBuilder;
import jp.co.soramitsu.iroha.android.sample.injection.ApplicationModule;

import static jp.co.soramitsu.iroha.android.sample.Constants.DOMAIN_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.ACCOUNT_DETAILS;
import static jp.co.soramitsu.iroha.android.sample.Constants.TRANSACTION_FAILED;
import static jp.co.soramitsu.iroha.android.sample.Constants.DELIMITER_FOR_ACCOUNT;
import static jp.co.soramitsu.iroha.android.sample.Constants.CONNECTION_TIMEOUT_SECONDS;

public class SetAccountDetailsInteractor extends CompletableInteractor<String> {

    private final ManagedChannel mChannel;
    private final PreferencesUtil mPreferencesUtil;
    private final ModelTransactionBuilder mTxBuilder;
    private final ModelProtoTransaction mProtoTxHelper;

    @Inject
    SetAccountDetailsInteractor(@Named(ApplicationModule.JOB) Scheduler jobScheduler,
                                @Named(ApplicationModule.UI) Scheduler uiScheduler,
                                @NonNull ManagedChannel managedChannel,
                                @NonNull PreferencesUtil preferencesUtil) {
        super(jobScheduler, uiScheduler);
        this.mChannel = managedChannel;
        this.mPreferencesUtil = preferencesUtil;
        this.mTxBuilder = new ModelTransactionBuilder();
        this.mProtoTxHelper = new ModelProtoTransaction();
    }

    @Override
    protected Completable build(@NonNull final String details) {
        return Completable.create(emitter -> {
            long currentTime = System.currentTimeMillis();
            Keypair userKeys = mPreferencesUtil.retrieveKeys();
            String username = mPreferencesUtil.retrieveUsername();
            String usernameId = username + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

            UnsignedTx setDetailsTransaction = mTxBuilder.creatorAccountId(usernameId)
                    .createdTime(BigInteger.valueOf(currentTime))
                    .setAccountDetail(usernameId, ACCOUNT_DETAILS, details)
                    .build();

            ByteVector txblob = mProtoTxHelper.signAndAddSignature(setDetailsTransaction, userKeys).blob();
            byte[] bs = toByteArray(txblob);

            BlockOuterClass.Transaction protoTx = null;
            try {
                protoTx = BlockOuterClass.Transaction.parseFrom(bs);
            } catch (InvalidProtocolBufferException e) {
                emitter.onError(e);
            }

            CommandServiceGrpc.CommandServiceBlockingStub stub = CommandServiceGrpc.newBlockingStub(mChannel)
                    .withDeadlineAfter(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            stub.torii(protoTx);

            if (!isTransactionSuccessful(stub, setDetailsTransaction)) {
                emitter.onError(new RuntimeException(TRANSACTION_FAILED));
            } else {
                emitter.onComplete();
            }
        });
    }
}
