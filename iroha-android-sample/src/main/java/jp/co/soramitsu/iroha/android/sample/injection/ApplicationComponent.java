package jp.co.soramitsu.iroha.android.sample.injection;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import jp.co.soramitsu.iroha.android.sample.main.MainActivity;
import jp.co.soramitsu.iroha.android.sample.main.history.HistoryFragment;
import jp.co.soramitsu.iroha.android.sample.main.send.SendFragment;
import jp.co.soramitsu.iroha.android.sample.registration.RegistrationActivity;
import lombok.NonNull;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector {

    void inject(@NonNull HistoryFragment historyFragment);

    void inject(@NonNull SendFragment sendFragment);

    void inject(@NonNull RegistrationActivity registrationActivity);

    void inject(@NonNull MainActivity mainActivity);
}