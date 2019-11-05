package com.donggeon.honmaker.extension.ted;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import gun0912.tedimagepicker.builder.TedRxImagePicker;
import io.reactivex.Single;

public class ImagePickerUtil {

    public static Single<Uri> startImagePicker(@NonNull final Context context) {
        return TedRxImagePicker.with(context)
                .image()
                .showCameraTile(false)
                .start();
    }
}
