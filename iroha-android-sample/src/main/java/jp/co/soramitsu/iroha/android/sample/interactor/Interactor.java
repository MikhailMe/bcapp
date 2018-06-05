package jp.co.soramitsu.iroha.android.sample.interactor;

import com.google.protobuf.ByteString;

import java.util.Iterator;

import lombok.NonNull;

import io.reactivex.Scheduler;
import iroha.protocol.Endpoint;
import iroha.protocol.Primitive;
import iroha.protocol.CommandServiceGrpc;
import jp.co.soramitsu.iroha.android.ByteVector;
import jp.co.soramitsu.iroha.android.UnsignedTx;
import io.reactivex.disposables.CompositeDisposable;

class Interactor {

    final Scheduler uiScheduler;
    final Scheduler jobScheduler;
    final CompositeDisposable subscriptions = new CompositeDisposable();

    Interactor(@NonNull Scheduler jobScheduler,
               @NonNull Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        this.jobScheduler = jobScheduler;
    }

    public void unsubscribe() {
        subscriptions.clear();
    }

    static byte[] toByteArray(ByteVector blob) {
        byte bs[] = new byte[(int) blob.size()];
        for (int i = 0; i < blob.size(); ++i) {
            bs[i] = (byte) blob.get(i);
        }
        return bs;
    }

    static boolean isTransactionSuccessful(@NonNull CommandServiceGrpc.CommandServiceBlockingStub stub,
                                           @NonNull UnsignedTx utx) {
        ByteVector txhash = utx.hash().blob();
        byte bshash[] = toByteArray(txhash);

        Endpoint.TxStatusRequest request = Endpoint.TxStatusRequest.newBuilder().setTxHash(ByteString.copyFrom(bshash)).build();

        Iterator<Endpoint.ToriiResponse> features = stub.statusStream(request);

        Endpoint.ToriiResponse response = null;
        while (features.hasNext()) {
            response = features.next();
        }
        return response.getTxStatus() == Endpoint.TxStatus.COMMITTED;
    }

    static String getIntBalance(@NonNull Primitive.Amount amount) {
        StringBuilder stringBuilder = new StringBuilder();
        if (amount.getValue().getFirst() != 0) {
            stringBuilder.append(amount.getValue().getFirst());
        }

        if (amount.getValue().getSecond() != 0) {
            stringBuilder.append(amount.getValue().getSecond());
        }

        if (amount.getValue().getThird() != 0) {
            stringBuilder.append(amount.getValue().getThird());
        }

        stringBuilder.append(amount.getValue().getFourth());

        return stringBuilder.toString();
    }
}
