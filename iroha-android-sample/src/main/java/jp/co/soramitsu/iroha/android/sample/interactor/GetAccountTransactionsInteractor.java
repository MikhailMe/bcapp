package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.math.BigInteger;

import lombok.NonNull;

import javax.inject.Named;
import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.Scheduler;
import io.grpc.ManagedChannel;
import iroha.protocol.Queries;
import iroha.protocol.Responses;
import iroha.protocol.BlockOuterClass;
import iroha.protocol.QueryServiceGrpc;
import jp.co.soramitsu.iroha.android.Keypair;
import jp.co.soramitsu.iroha.android.ByteVector;
import jp.co.soramitsu.iroha.android.UnsignedQuery;
import jp.co.soramitsu.iroha.android.ModelProtoQuery;
import jp.co.soramitsu.iroha.android.ModelQueryBuilder;
import jp.co.soramitsu.iroha.android.sample.PreferencesUtil;
import jp.co.soramitsu.iroha.android.sample.transaction.Transaction;
import jp.co.soramitsu.iroha.android.sample.injection.ApplicationModule;

import static jp.co.soramitsu.iroha.android.sample.Constants.ASSET_ID;
import static jp.co.soramitsu.iroha.android.sample.Constants.DOMAIN_ID;
import static iroha.protocol.Commands.Command.CommandCase.TRANSFER_ASSET;
import static jp.co.soramitsu.iroha.android.sample.Constants.QUERY_COUNTER;
import static jp.co.soramitsu.iroha.android.sample.Constants.DELIMITER_FOR_ACCOUNT;

public class GetAccountTransactionsInteractor extends SingleInteractor<List<Transaction>, Void> {

    private final ManagedChannel mChannel;
    private final PreferencesUtil mPreferencesUtil;
    private final ModelProtoQuery mProtoQueryHelper;
    private final ModelQueryBuilder mModelQueryBuilder;

    @Inject
    GetAccountTransactionsInteractor(@Named(ApplicationModule.JOB) Scheduler jobScheduler,
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
    protected Single<List<Transaction>> build(Void v) {
        return Single.create(emitter -> {
            long currentTime = System.currentTimeMillis();
            Keypair userKeys = mPreferencesUtil.retrieveKeys();
            String username = mPreferencesUtil.retrieveUsername();
            String usernameId = username + DELIMITER_FOR_ACCOUNT + DOMAIN_ID;

            UnsignedQuery accountBalanceQuery = mModelQueryBuilder
                    .creatorAccountId(usernameId)
                    .queryCounter(BigInteger.valueOf(QUERY_COUNTER))
                    .createdTime(BigInteger.valueOf(currentTime))
                    .getAccountAssetTransactions(usernameId, ASSET_ID)
                    .build();

            ByteVector queryBlob = mProtoQueryHelper.signAndAddSignature(accountBalanceQuery, userKeys).blob();
            byte bquery[] = toByteArray(queryBlob);

            Queries.Query protoQuery = null;
            try {
                protoQuery = Queries.Query.parseFrom(bquery);
            } catch (InvalidProtocolBufferException e) {
                emitter.onError(e);
            }

            QueryServiceGrpc.QueryServiceBlockingStub queryStub = QueryServiceGrpc.newBlockingStub(mChannel);
            Responses.QueryResponse queryResponse = queryStub.find(protoQuery);

            List<Transaction> transactions = new ArrayList<>();

            for (BlockOuterClass.Transaction transaction : queryResponse.getTransactionsResponse().getTransactionsList()) {
                if (transaction.getPayload().getCommands(0).getCommandCase() == TRANSFER_ASSET) {
                    Date date = new Date();
                    date.setTime(transaction.getPayload().getCreatedTime());

                    Long amount = Long.parseLong(getIntBalance(transaction.getPayload().getCommands(0).getTransferAsset().getAmount()));

                    String sender = transaction.getPayload().getCommands(0).getTransferAsset().getSrcAccountId();
                    String receiver = transaction.getPayload().getCommands(0).getTransferAsset().getDestAccountId();
                    String user = sender;

                    if (sender.equals(usernameId)) {
                        amount = -amount;
                        user = receiver;
                    }

                    if (receiver.equals(usernameId)) {
                        user = sender;
                    }

                    transactions.add(new Transaction(0, date, user.split(DELIMITER_FOR_ACCOUNT)[0], amount));
                }
            }
            emitter.onSuccess(transactions);
        });
    }
}
