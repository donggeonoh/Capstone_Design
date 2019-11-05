package com.donggeon.honmaker.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.donggeon.honmaker.extension.livedata.SingleLiveEvent;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    protected final MutableLiveData<Throwable> error = new MutableLiveData<>();
    @NonNull
    private final SingleLiveEvent<Boolean> finishView = new SingleLiveEvent<>();

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    @NonNull
    public LiveData<Boolean> getFinishView() {
        return finishView;
    }
}