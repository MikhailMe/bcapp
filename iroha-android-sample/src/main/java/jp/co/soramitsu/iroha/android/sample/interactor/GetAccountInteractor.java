package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.protobuf.InvalidProtocolBufferException;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.grpc.ManagedChannel;
import io.reactivex.Scheduler;
import iroha.protocol.Queries;
import iroha.protocol.Responses;
import iroha.protocol.QueryServiceGrpc;
import jp.co.soramitsu.iroha.android.Keypair;
import jp.co.soramitsu.iroha.android.ByteVector;
import jp.co.soramitsu.iroha.android.ModelCrypto;
import jp.co.soramitsu.iroha.android.UnsignedQuery;
import jp.co.soramitsu.iroha.android.ModelProtoQuery;
import jp.co.soramitsu.iroha.android.ModelQueryBuilder;
import jp.co.soramitsu.iroha.android.sample.injection.ApplicationModule;

import static jp.co.soramitsu.iroha.android.sample.Constants.DOMAIN_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.CREATOR_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.PUBLIC_KEY;
import static jp.co.soramitsu.iroha.android.sample.Constants.PRIVATE_KEY;
import static jp.co.soramitsu.iroha.android.sample.Constants.QUERY_COUNTER;
import static jp.co.soramitsu.iroha.android.sample.Constants.DELIMITER_FOR_ACCOUNT;
import static jp.co.soramitsu.iroha.android.sample.Constants.CONNECTION_TIMEOUT_SECONDS;

public class GetAccountInteractor extends SingleInteractor<Responses.Account, String> {

    private final ModelCrypto mCrypto;
    private final ManagedChannel mChannel;
    private final ModelProtoQuery mProtoQueryHelper;
    private final ModelQueryBuilder mModelQueryBuilder;

    @Inject
    GetAccountInteractor(@Named(ApplicationModule.JOB) Scheduler jobScheduler,
                         @Named(ApplicationModule.UI) Scheduler uiScheduler,
                         @NonNull ModelCrypto crypto,
                         @NonNull ManagedChannel channel) {
        super(jobScheduler, uiScheduler);
        this.mCrypto = crypto;
        this.mChannel = channel;
        this.mProtoQueryHelper = new ModelProtoQuery();
        this.mModelQueryBuilder = new ModelQueryBuilder();
    }

    @Override
    protected Single<Responses.Account> build(@NonNull final String accountCaption) {
        return Single.create(emitter -> {
            long currentTime = System.currentTimeMillis();
            Keypair adminKeys = mCrypto.convertFromExisting(PUBLIC_KEY, PRIVATE_KEY);
            String accountId = accountCaption + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

            UnsignedQuery query = mModelQueryBuilder
                    .createdTime(BigInteger.valueOf(currentTime))
                    .queryCounter(BigInteger.valueOf(QUERY_COUNTER))
                    .creatorAccountId(CREATOR_ID)
                    .getAccount(accountId)
                    .build();

            ByteVector queryBlob = mProtoQueryHelper.signAndAddSignature(query, adminKeys).blob();
            byte bquery[] = toByteArray(queryBlob);

            Queries.Query protoQuery = null;
            try {
                protoQuery = Queries.Query.parseFrom(bquery);
            } catch (InvalidProtocolBufferException e) {
                emitter.onError(e);
            }

            QueryServiceGrpc.QueryServiceBlockingStub queryStub = QueryServiceGrpc.newBlockingStub(mChannel)
                    .withDeadlineAfter(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            Responses.QueryResponse queryResponse = queryStub.find(protoQuery);

            emitter.onSuccess(queryResponse.getAccountResponse().getAccount());
        });
    }
}
