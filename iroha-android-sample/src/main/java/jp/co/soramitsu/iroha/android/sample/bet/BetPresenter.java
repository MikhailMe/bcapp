package jp.co.soramitsu.iroha.android.sample.bet;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.SendAssetInteractor;
import lombok.NonNull;
import lombok.Setter;

public class BetPresenter {

    @Setter
    private BetActivity mBetActivity;

    private final SendAssetInteractor sendAssetInteractor;
    private final GetAccountInteractor getAccountInteractor;

    @Inject
    public BetPresenter(SendAssetInteractor sendAssetInteractor,
                         GetAccountInteractor getAccountInteractor) {
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
                    mBetActivity.didSendError(new Throwable(mBetActivity.getString(R.string.not_enough_balance_error)));
                }
            } else {
                mBetActivity.didSendError(new Throwable(SampleApplication.instance.getString(R.string.server_is_not_reachable)));
            }
        } else {
            mBetActivity.didSendError(new Throwable(SampleApplication.instance.getString(R.string.fields_cant_be_empty)));
        }
    }

    private void checkAccountAndSendTransaction(@NonNull final String[] data) {
        getAccountInteractor.execute(data[0],
                account -> {
                    if (account.getAccountId().isEmpty()) {
                        mBetActivity.didSendError(new Throwable(SampleApplication.instance.getString(R.string.username_doesnt_exists)));
                    } else {
                        executeSend(data);
                    }
                }, throwable -> mBetActivity.didSendError(throwable));
    }

    private void executeSend(@NonNull final String[] data) {
        sendAssetInteractor.execute(data,
                mBetActivity::didSendSuccess,
                mBetActivity::didSendError
        );
    }

    private boolean isEnoughBalance(final long amount) {
        return SampleApplication.instance.account.getBalance() >= amount;
    }
}
