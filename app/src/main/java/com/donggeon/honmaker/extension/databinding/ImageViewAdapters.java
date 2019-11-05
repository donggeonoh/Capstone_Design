package com.donggeon.honmaker.extension.databinding;

import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.File;

public class ImageViewAdapters {

    @BindingAdapter({"url"})
    public static void loadBitmapImage(AppCompatImageView imageView, String imageUri) {
        if (imageUri == null) {
            return;
        }

        Glide.with(imageView).load(imageUri).into(imageView);
    }

    @BindingAdapter({"resId"})
    public static void loadBitmapImage(AppCompatImageView imageView, int resId) {
        Glide.with(imageView).load(resId).into(imageView);
    }

    @BindingAdapter({"file"})
    public static void loadImageFromFile(AppCompatImageView imageView, String filePath) {
        if (filePath == null) {
            return;
        }

        Glide.with(imageView).load(new File(filePath)).into(imageView);
    }

}
