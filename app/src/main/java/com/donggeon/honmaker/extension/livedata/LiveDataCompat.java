package com.donggeon.honmaker.extension.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class LiveDataCompat {

    @NonNull
    public static <T> T getValue(@NonNull final LiveData<T> liveData,
                                 @NonNull final T defaultValue) {
        return liveData.getValue() == null ? defaultValue : liveData.getValue();
    }
}
