package com.donggeon.honmaker.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    protected final MutableLiveData<? super Throwable> error = new MutableLiveData<>();

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}