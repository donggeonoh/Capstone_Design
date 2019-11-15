package com.donggeon.honmaker.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.donggeon.honmaker.extension.livedata.SingleLiveEvent;
import com.donggeon.honmaker.extension.rxjava.AutoDisposableOwner;
import com.donggeon.honmaker.extension.rxjava.AutoDisposeException;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.CompletableSubject;

public abstract class BaseViewModel extends ViewModel implements AutoDisposableOwner {

    @NonNull
    private CompletableSubject autoDisposeSignal = CompletableSubject.create();
    @NonNull
    protected MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    protected final SingleLiveEvent<Boolean> finish = new SingleLiveEvent<>();
    @NonNull
    protected final SingleLiveEvent<Boolean> loading = new SingleLiveEvent<>();

    @Override
    protected void onCleared() {
        autoDisposeSignal.onComplete();
        super.onCleared();
    }

    public Single<Object> autoDisposeSignalSingle() {
        return autoDisposeSignal.andThen(Single.error(new AutoDisposeException()));
    }

    public Maybe<Object> autoDisposeSignalMaybe() {
        return autoDisposeSignal.andThen(Maybe.error(new AutoDisposeException()));
    }
    
    public Observable<Object> autoDisposeSignalObservable() {
        return autoDisposeSignal.andThen(Observable.error(new AutoDisposeException()));
    }

    public Completable autoDisposeSignalCompletable() {
        return autoDisposeSignal.andThen(Completable.error(new AutoDisposeException()));
    }

    @NonNull
    public LiveData<Throwable> getError() {
        return error;
    }

    @NonNull
    public LiveData<Boolean> getFinish() {
        return finish;
    }

    @NonNull
    public LiveData<Boolean> getLoading() {
        return loading;
    }
}