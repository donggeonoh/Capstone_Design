package com.donggeon.honmaker.extension.rxjava;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface AutoDisposableOwner {

    Single<Object> autoDisposeSignalSingle();

    Maybe<Object> autoDisposeSignalMaybe();

    Observable<Object> autoDisposeSignalObservable();

    Completable autoDisposeSignalCompletable();

}
