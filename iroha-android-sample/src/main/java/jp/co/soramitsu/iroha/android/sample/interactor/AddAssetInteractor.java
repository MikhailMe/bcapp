package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.protobuf.InvalidProtocolBufferException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import javax.inject.Named;
import javax.inject.Inject;

import io.grpc.ManagedChannel;
import io.reactivex.Scheduler;
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

import static jp.co.soramitsu.iroha.android.sample.Constants.ASSET_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.DOMAIN_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.CREATOR_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.PUBLIC_KEY;
import static jp.co.soramitsu.iroha.android.sample.Constants.PRIVATE_KEY;
import static jp.co.soramitsu.iroha.android.sample.Constants.DEFAULT_BALANCE;
import static jp.co.soramitsu.iroha.android.sample.Constants.TRANSACTION_FAILED;
import static jp.co.soramitsu.iroha.android.sample.Constants.DELIMITER_FOR_ACCOUNT;
import static jp.co.soramitsu.iroha.android.sample.Constants.CONNECTION_TIMEOUT_SECONDS;

public class AddAssetInteractor extends CompletableInteractor<String> {

    private final ModelCrypto mCrypto;
    private final ManagedChannel mChannel;
    private final PreferencesUtil mPreferencesUtil;
    private final ModelTransactionBuilder mTxBuilder = new ModelTransactionBuilder();
    private final ModelProtoTransaction mProtoTxHelper = new ModelProtoTransaction();

    @Inject
    AddAssetInteractor(@Named(ApplicationModule.JOB) Scheduler jobScheduler,
                       @Named(ApplicationModule.UI) Scheduler uiScheduler,
                       @NonNull ManagedChannel managedChannel,
                       @NonNull PreferencesUtil preferencesUtil,
                       @NonNull ModelCrypto crypto) {
        super(jobScheduler, uiScheduler);
        this.mCrypto = crypto;
        this.mChannel = managedChannel;
        this.mPreferencesUtil = preferencesUtil;
    }

    @Override
    protected Completable build(@NonNull final String details) {
        return Completable.create(emitter -> {
            long currentTime = System.currentTimeMillis();
            Keypair adminKeys = mCrypto.convertFromExisting(PUBLIC_KEY, PRIVATE_KEY);
            String username = mPreferencesUtil.retrieveUsername();
            String usernameId = username + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

            UnsignedTx addAssetTx = mTxBuilder.creatorAccountId(CREATOR_ID)
                    .createdTime(BigInteger.valueOf(currentTime))
                    .addAssetQuantity(CREATOR_ID, ASSET_ID, DEFAULT_BALANCE)
                    .transferAsset(CREATOR_ID, usernameId, ASSET_ID, "initial", DEFAULT_BALANCE)
                    .build();

            ByteVector txblob = mProtoTxHelper.signAndAddSignature(addAssetTx, adminKeys).blob();
            byte[] bsq = toByteArray(txblob);

            BlockOuterClass.Transaction protoTx = null;
            try {
                protoTx = BlockOuterClass.Transaction.parseFrom(bsq);
            } catch (InvalidProtocolBufferException e) {
                emitter.onError(e);
            }

            CommandServiceGrpc.CommandServiceBlockingStub stub = CommandServiceGrpc.newBlockingStub(mChannel)
                    .withDeadlineAfter(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            stub.torii(protoTx);

            if (!isTransactionSuccessful(stub, addAssetTx)) {
                emitter.onError(new RuntimeException(TRANSACTION_FAILED));
            } else {
                emitter.onComplete();
            }
        });
    }
}
