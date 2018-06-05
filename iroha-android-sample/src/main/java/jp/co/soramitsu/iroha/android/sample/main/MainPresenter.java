package jp.co.soramitsu.iroha.android.sample.main;

import lombok.NonNull;
import lombok.Setter;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.data.Account;
import jp.co.soramitsu.iroha.android.sample.PreferencesUtil;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountBalanceInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountDetailsInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.SetAccountDetailsInteractor;

import static jp.co.soramitsu.iroha.android.sample.Constants.ASSET_CAPTION;

public class MainPresenter {

    private final PreferencesUtil mPreferencesUtil;
    private final GetAccountInteractor mGetAccountInteractor;
    private final SetAccountDetailsInteractor mSetAccountDetails;
    private final GetAccountDetailsInteractor mGetAccountDetails;
    private final GetAccountBalanceInteractor mGetAccountBalanceInteractor;

    @Setter
    private MainView mView;

    @Inject
    public MainPresenter(@NonNull PreferencesUtil preferencesUtil,
                         @NonNull GetAccountInteractor getAccountInteractor,
                         @NonNull SetAccountDetailsInteractor setAccountDetails,
                         @NonNull GetAccountDetailsInteractor getAccountDetails,
                         @NonNull GetAccountBalanceInteractor getAccountBalanceInteractor) {
        this.mPreferencesUtil = preferencesUtil;
        this.mSetAccountDetails = setAccountDetails;
        this.mGetAccountDetails = getAccountDetails;
        this.mGetAccountInteractor = getAccountInteractor;
        this.mGetAccountBalanceInteractor = getAccountBalanceInteractor;
    }

    void onCreate() {
        updateData(false);
    }

    void updateData(boolean fromRefresh) {
        String username = mPreferencesUtil.retrieveUsername();
        mView.setUsername(username);

        mGetAccountInteractor.execute(username,
                account -> {
                    SampleApplication.instance.account = new Account(account, -1);
                    mGetAccountBalanceInteractor.execute(
                            balance -> {
                                if (fromRefresh) {
                                    mView.hideRefresh();
                                }
                                mView.setAccountBalance(balance + " " + ASSET_CAPTION);
                                SampleApplication.instance.account.setBalance(Long.parseLong(balance));
                            },
                            mView::showError);
                },
                mView::showError
        );

        mGetAccountDetails.execute(
                mView::setAccountDetails,
                mView::showError
        );
    }

    void logout() {
        mPreferencesUtil.clear();
        mView.showRegistrationScreen();
    }

    void setAccountDetails(@NonNull final String details) {
        mView.showProgress();
        mSetAccountDetails.execute(details, () -> {
            mView.hideProgress();
            mView.setAccountDetails(details);
        }, throwable -> {
            mView.showError(throwable);
            mView.hideProgress();
        });
    }

    void onStop() {
        mView = null;
        mSetAccountDetails.unsubscribe();
        mGetAccountDetails.unsubscribe();
        mGetAccountInteractor.unsubscribe();
        mGetAccountBalanceInteractor.unsubscribe();
    }
}
