package com.donggeon.honmaker.utils;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.donggeon.honmaker.App;

import java.util.concurrent.ExecutionException;

public class ImageUtil {

    @Nullable
    public static Bitmap getBitmapFromUrl(@NonNull final String imageUrl) {
        try {
            return Glide.with(App.instance().getContext())
                    .asBitmap()
                    .skipMemoryCache(true)
                    .load(imageUrl)
                    .override(1000)
                    .submit()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
