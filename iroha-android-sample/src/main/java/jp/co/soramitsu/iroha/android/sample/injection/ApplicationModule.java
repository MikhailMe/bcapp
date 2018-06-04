package jp.co.soramitsu.iroha.android.sample.injection;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.co.soramitsu.iroha.android.ModelCrypto;

@Module
public class ApplicationModule {
    public static final String JOB = "JOB";
    public static final String UI = "UI";

    private static final int PORT = 50051;
    private static final String HOME_IP = "172.16.0.7";
    private static final String BROVKI_IP = "192.168.43.81";

    @Provides
    @Singleton
    @Named(JOB)
    public Scheduler provideJobScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @Named(UI)
    public Scheduler provideUIScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    public ManagedChannel provideManagedChannel() {
        return ManagedChannelBuilder.forAddress(HOME_IP, PORT).usePlaintext().build();
    }

    @Provides
    @Singleton
    public ModelCrypto provideModelCrypto() {
        return new ModelCrypto();
    }

}
