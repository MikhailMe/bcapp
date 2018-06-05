package jp.co.soramitsu.iroha.android.sample.injection;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import jp.co.soramitsu.iroha.android.sample.bet.BetActivity;
import jp.co.soramitsu.iroha.android.sample.main.MainActivity;
import jp.co.soramitsu.iroha.android.sample.main.history.HistoryFragment;
import jp.co.soramitsu.iroha.android.sample.registration.RegistrationActivity;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector {

    void inject(BetActivity betActivity);

    void inject(MainActivity mainActivity);

    void inject(HistoryFragment historyFragment);

    void inject(RegistrationActivity registrationActivity);
}
