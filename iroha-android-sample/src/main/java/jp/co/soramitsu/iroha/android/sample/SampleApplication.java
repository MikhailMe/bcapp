package jp.co.soramitsu.iroha.android.sample;

import lombok.Getter;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.AndroidLogAdapter;

import jp.co.soramitsu.iroha.android.sample.data.Account;
import jp.co.soramitsu.iroha.android.sample.injection.ApplicationComponent;
import jp.co.soramitsu.iroha.android.sample.injection.DaggerApplicationComponent;

public class SampleApplication extends Application {
    static {
        try {
            System.loadLibrary("irohajava");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account account;

    public static SampleApplication instance;

    @Getter
    @VisibleForTesting
    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationComponent = DaggerApplicationComponent.builder().build();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
