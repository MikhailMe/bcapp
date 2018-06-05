package jp.co.soramitsu.iroha.android.sample.registration;

import android.support.annotation.VisibleForTesting;

import lombok.Setter;
import lombok.NonNull;

import javax.inject.Inject;

import jp.co.soramitsu.iroha.android.sample.R;
import jp.co.soramitsu.iroha.android.sample.PreferencesUtil;
import jp.co.soramitsu.iroha.android.sample.SampleApplication;
import jp.co.soramitsu.iroha.android.sample.interactor.AddAssetInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.CreateAccountInteractor;
import jp.co.soramitsu.iroha.android.sample.interactor.GetAccountInteractor;

public class RegistrationPresenter {

    @Setter
    private RegistrationView mView;
    private final PreferencesUtil mPreferencesUtil;
    private final AddAssetInteractor mAddAssetInteractor;
    private final GetAccountInteractor mGetAccountInteractor;
    private final CreateAccountInteractor mCreateAccountInteractor;

    @VisibleForTesting
    public boolean isRequestFinished;

    @Inject
    public RegistrationPresenter(@NonNull CreateAccountInteractor createAccountInteractor,
                                 @NonNull GetAccountInteractor getAccountInteractor,
                                 @NonNull AddAssetInteractor addAssetInteractor,
                                 @NonNull PreferencesUtil preferencesUtil) {
        this.mPreferencesUtil = preferencesUtil;
        this.mAddAssetInteractor = addAssetInteractor;
        this.mGetAccountInteractor = getAccountInteractor;
        this.mCreateAccountInteractor = createAccountInteractor;
    }

    void createAccount(@NonNull final String username) {
        isRequestFinished = false;

        if (!username.isEmpty()) {
            mGetAccountInteractor.execute(username, account -> {
                if (account.getAccountId().isEmpty()) {
                    mCreateAccountInteractor.execute(username,
                            () -> mAddAssetInteractor.execute(username,
                                    () -> {
                                        isRequestFinished = true;
                                        mView.didRegistrationSuccess();
                                    },
                                    this::didRegistrationError
                            ),
                            this::didRegistrationError);
                } else {
                    didRegistrationError(new Throwable(SampleApplication.instance
                            .getString(R.string.username_already_exists_error_dialog_message)));
                }
            }, this::didRegistrationError);
        } else {
            didRegistrationError(new Throwable(SampleApplication.instance.getString(R.string.username_empty_error_dialog_message)));
        }
    }

    private void didRegistrationError(Throwable throwable) {
        isRequestFinished = true;
        mPreferencesUtil.clear();
        mView.didRegistrationError(throwable);
    }

    boolean isRegistered() {
        return !mPreferencesUtil.retrieveUsername().isEmpty();
    }

    void onStop() {
        mView = null;
        mAddAssetInteractor.unsubscribe();
        mGetAccountInteractor.unsubscribe();
        mCreateAccountInteractor.unsubscribe();
    }
}
