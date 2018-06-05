package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.protobuf.InvalidProtocolBufferException;

import java.math.BigInteger;

import lombok.NonNull;

import javax.inject.Named;
import javax.inject.Inject;

import io.reactivex.Single;
import io.grpc.ManagedChannel;
import io.reactivex.Scheduler;
import iroha.protocol.Queries;
import iroha.protocol.Responses;
import iroha.protocol.QueryServiceGrpc;
import jp.co.soramitsu.iroha.android.Keypair;
import jp.co.soramitsu.iroha.android.ByteVector;
import jp.co.soramitsu.iroha.android.UnsignedQuery;
import jp.co.soramitsu.iroha.android.ModelProtoQuery;
import jp.co.soramitsu.iroha.android.sample.Constants;
import jp.co.soramitsu.iroha.android.ModelQueryBuilder;
import jp.co.soramitsu.iroha.android.sample.PreferencesUtil;
import jp.co.soramitsu.iroha.android.sample.injection.ApplicationModule;

import static jp.co.soramitsu.iroha.android.sample.Constants.DELIMITER_FOR_ACCOUNT;
import static jp.co.soramitsu.iroha.android.sample.Constants.DOMAIN_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.QUERY_COUNTER;

public class GetAccountDetailsInteractor extends SingleInteractor<String, Void> {

    private final ManagedChannel mChannel;
    private final PreferencesUtil mPreferencesUtil;
    private final ModelProtoQuery mProtoQueryHelper;
    private final ModelQueryBuilder mModelQueryBuilder;

    @Inject
    GetAccountDetailsInteractor(@Named(ApplicationModule.JOB) Scheduler jobScheduler,
                                @Named(ApplicationModule.UI) Scheduler uiScheduler,
                                @NonNull PreferencesUtil preferenceUtils,
                                @NonNull ManagedChannel channel) {
        super(jobScheduler, uiScheduler);
        this.mChannel = channel;
        this.mPreferencesUtil = preferenceUtils;
        this.mProtoQueryHelper = new ModelProtoQuery();
        this.mModelQueryBuilder = new ModelQueryBuilder();
    }

    @Override
    protected Single<String> build(Void v) {
        return Single.create(emitter -> {
            long currentTime = System.currentTimeMillis();
            Keypair userKeys = mPreferencesUtil.retrieveKeys();
            String username = mPreferencesUtil.retrieveUsername();
            String usernameId = username + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

            UnsignedQuery accountDetails = mModelQueryBuilder.creatorAccountId(usernameId)
                    .queryCounter(BigInteger.valueOf(QUERY_COUNTER))
                    .createdTime(BigInteger.valueOf(currentTime))
                    .getAccountDetail(usernameId)
                    .build();

            ByteVector queryBlob = mProtoQueryHelper.signAndAddSignature(accountDetails, userKeys).blob();
            byte bquery[] = toByteArray(queryBlob);

            Queries.Query protoQuery = null;
            try {
                protoQuery = Queries.Query.parseFrom(bquery);
            } catch (InvalidProtocolBufferException e) {
                emitter.onError(e);
            }

            QueryServiceGrpc.QueryServiceBlockingStub queryStub = QueryServiceGrpc.newBlockingStub(mChannel);
            Responses.QueryResponse queryResponse = queryStub.find(protoQuery);

            JsonElement jsonElement = new Gson().fromJson(queryResponse.getAccountDetailResponse().getDetail(), JsonObject.class).get(usernameId);

            String detail = jsonElement != null ? jsonElement.getAsJsonObject().get(Constants.ACCOUNT_DETAILS).getAsString() : "";

            emitter.onSuccess(detail);
        });
    }
}
