package jp.co.soramitsu.iroha.android.sample.interactor;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import lombok.NonNull;

public abstract class CompletableInteractor<ParameterType> extends Interactor {

    CompletableInteractor(@NonNull Scheduler jobScheduler,
                          @NonNull Scheduler uiScheduler) {
        super(jobScheduler, uiScheduler);
    }

    protected abstract Completable build(@NonNull ParameterType parameter);

    public void execute(@NonNull ParameterType parameter,
                        @NonNull Action onSuccess,
                        @NonNull Consumer<Throwable> onError) {
        subscriptions.add(build(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
                .subscribe(onSuccess, onError));
    }
}