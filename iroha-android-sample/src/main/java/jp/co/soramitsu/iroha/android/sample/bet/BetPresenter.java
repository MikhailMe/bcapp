package jp.co.soramitsu.iroha.android.sample.bet;

import lombok.Setter;
import lombok.NonNull;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.SendAssetInteractor;
import jp.co.soramitsu.iroha.android.sample.transaction.TransactionData;

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

    void sendTransaction(@NonNull final TransactionData td) {
        if (!td.getName().isEmpty() && !td.getBetSum().isEmpty()) {
            if (SampleApplication.instance.account != null) {
                if (isEnoughBalance(Long.parseLong(td.getBetSum()))) {
                    checkAccountAndSendTransaction(td);
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

    private void checkAccountAndSendTransaction(@NonNull final TransactionData td) {
        getAccountInteractor.execute(td.getName(),
                account -> {
                    if (account.getAccountId().isEmpty()) {
                        mBetActivity.didSendError(new Throwable(SampleApplication.instance.getString(R.string.username_doesnt_exists)));
                    } else {
                        executeSend(td);
                    }
                }, throwable -> mBetActivity.didSendError(throwable));
    }

    private void executeSend(@NonNull final TransactionData td) {
        sendAssetInteractor.execute(td,
                mBetActivity::didSendSuccess,
                mBetActivity::didSendError
        );
    }

    private boolean isEnoughBalance(final long amount) {
        return SampleApplication.instance.account.getBalance() >= amount;
    }
}
