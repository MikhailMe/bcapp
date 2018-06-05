package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.protobuf.InvalidProtocolBufferException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Inject;

import lombok.NonNull;
import io.reactivex.Scheduler;
import io.grpc.ManagedChannel;
import io.reactivex.Completable;
import iroha.protocol.BlockOuterClass;
import iroha.protocol.CommandServiceGrpc;
import jp.co.soramitsu.iroha.android.Keypair;
import jp.co.soramitsu.iroha.android.UnsignedTx;
import jp.co.soramitsu.iroha.android.ByteVector;
import jp.co.soramitsu.iroha.android.ModelCrypto;
import jp.co.soramitsu.iroha.android.ModelProtoTransaction;
import jp.co.soramitsu.iroha.android.sample.PreferencesUtil;
import jp.co.soramitsu.iroha.android.ModelTransactionBuilder;
import jp.co.soramitsu.iroha.android.sample.injection.ApplicationModule;

import static jp.co.soramitsu.iroha.android.sample.Constants.DOMAIN_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.CREATOR_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.PUBLIC_KEY;
import static jp.co.soramitsu.iroha.android.sample.Constants.PRIVATE_KEY;
import static jp.co.soramitsu.iroha.android.sample.Constants.TRANSACTION_FAILED;
import static jp.co.soramitsu.iroha.android.sample.Constants.DELIMITER_FOR_ACCOUNT;
import static jp.co.soramitsu.iroha.android.sample.Constants.CONNECTION_TIMEOUT_SECONDS;

public class CreateAccountInteractor extends CompletableInteractor<String> {

    private final ModelCrypto mCrypto;
    private final ManagedChannel mChannel;
    private final PreferencesUtil mPreferencesUtil;
    private final ModelTransactionBuilder mTxBuilder;
    private final ModelProtoTransaction mProtoTxHelper;

    @Inject
    CreateAccountInteractor(@Named(ApplicationModule.JOB) Scheduler jobScheduler,
                            @Named(ApplicationModule.UI) Scheduler uiScheduler,
                            @NonNull ManagedChannel managedChannel,
                            @NonNull ModelCrypto crypto,
                            @NonNull PreferencesUtil preferencesUtil) {
        super(jobScheduler, uiScheduler);
        this.mCrypto = crypto;
        this.mChannel = managedChannel;
        this.mPreferencesUtil = preferencesUtil;
        this.mTxBuilder = new ModelTransactionBuilder();
        this.mProtoTxHelper = new ModelProtoTransaction();
    }

    @Override
    protected Completable build(@NonNull final String username) {
        return Completable.create(emitter -> {
            long currentTime = System.currentTimeMillis();
            Keypair userKeys = mCrypto.generateKeypair();
            Keypair adminKeys = mCrypto.convertFromExisting(PUBLIC_KEY, PRIVATE_KEY);
            String usernameId = username + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

            UnsignedTx createAccount = mTxBuilder.creatorAccountId(CREATOR_ID)
                    .createdTime(BigInteger.valueOf(currentTime))
                    .createAccount(username, DOMAIN_ID, userKeys.publicKey())
                    .setAccountQuorum(usernameId, 1)
                    .build();

            ByteVector txblob = mProtoTxHelper.signAndAddSignature(createAccount, adminKeys).blob();
            byte bs[] = toByteArray(txblob);

            BlockOuterClass.Transaction protoTx = null;
            try {
                protoTx = BlockOuterClass.Transaction.parseFrom(bs);
            } catch (InvalidProtocolBufferException e) {
                emitter.onError(e);
            }

            CommandServiceGrpc.CommandServiceBlockingStub stub = CommandServiceGrpc.newBlockingStub(mChannel)
                    .withDeadlineAfter(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            stub.torii(protoTx);

            if (!isTransactionSuccessful(stub, createAccount)) {
                emitter.onError(new RuntimeException(TRANSACTION_FAILED));
            } else {
                mPreferencesUtil.saveKeys(userKeys);
                mPreferencesUtil.saveUsername(username);
                emitter.onComplete();
            }
        });
    }
}
