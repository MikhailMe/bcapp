package jp.co.soramitsu.iroha.android.sample.main.send;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.SendAssetInteractor;
import lombok.NonNull;
import lombok.Setter;

public class SendPresenter {

    @Setter
    private SendFragment fragment;

    private final SendAssetInteractor sendAssetInteractor;
    private final GetAccountInteractor getAccountInteractor;

    public static final int REQUEST_CODE_QR_SCAN = 101;

    @Inject
    public SendPresenter(SendAssetInteractor sendAssetInteractor, GetAccountInteractor getAccountInteractor) {
        this.sendAssetInteractor = sendAssetInteractor;
        this.getAccountInteractor = getAccountInteractor;
    }

    void sendTransaction(@NonNull final String username,
                         @NonNull final String amount) {
        String[] data = {username, amount};

        if (!username.isEmpty() && !amount.isEmpty()) {
            if (SampleApplication.instance.account != null) {
                if (isEnoughBalance(Long.parseLong(amount))) {
                    checkAccountAndSendTransaction(data);
                } else {
                    fragment.didSendError(new Throwable(fragment.getString(R.string.not_enough_balance_error)));
                }
            } else {
                fragment.didSendError(new Throwable(SampleApplication.instance.getString(R.string.server_is_not_reachable)));
            }
        } else {
            fragment.didSendError(new Throwable(SampleApplication.instance.getString(R.string.fields_cant_be_empty)));
        }
    }

    private void checkAccountAndSendTransaction(@NonNull String[] data) {
        getAccountInteractor.execute(data[0],
                account -> {
                    if (account.getAccountId().isEmpty()) {
                        fragment.didSendError(new Throwable(SampleApplication.instance.getString(R.string.username_doesnt_exists)));
                    } else {
                        executeSend(data);
                    }
                }, throwable -> fragment.didSendError(throwable));
    }

    private void executeSend(@NonNull String[] data) {
        sendAssetInteractor.execute(data,
                () -> fragment.didSendSuccess(),
                error -> fragment.didSendError(error)
        );
    }

    private boolean isEnoughBalance(final long amount) {
        return SampleApplication.instance.account.getBalance() >= amount;
    }

    void onStop() {
        fragment = null;
        sendAssetInteractor.unsubscribe();
        getAccountInteractor.unsubscribe();
    }
}
