package jp.co.soramitsu.iroha.android.sample.interactor;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import lombok.NonNull;

public abstract class SingleInteractor<ResultType, ParameterType> extends Interactor {

    SingleInteractor(@NonNull Scheduler jobScheduler,
                     @NonNull Scheduler uiScheduler) {
        super(jobScheduler, uiScheduler);
    }

    protected abstract Single<ResultType> build(ParameterType parameter);

    public void execute(ParameterType parameter,
                        @NonNull Consumer<ResultType> onSuccess,
                        @NonNull Consumer<Throwable> onError) {
        subscriptions.add(build(parameter)
                .subscribeOn(jobScheduler)
                .observeOn(uiScheduler)
                .subscribe(onSuccess, onError));
    }

    public void execute(Consumer<ResultType> onSuccess, Consumer<Throwable> onError) {
        execute(null, onSuccess, onError);
    }
}
