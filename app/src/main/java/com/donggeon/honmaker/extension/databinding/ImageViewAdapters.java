package com.donggeon.honmaker.extension.databinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * DataBinding 을 위한 어댑터
 * 여기 있는건 ImageView 에 적용할 수 있는 어댑터들
 */
public class ImageViewAdapters {

    /**
     * drawable 폴더에 있는 리소스를 ImageView 에 랜더링
     */
    @BindingAdapter({"resId"})
    public static void loadBitmapImage(@NonNull final AppCompatImageView imageView,
                                       final int resId) {
        // Glide 를 이용해서 resId 를 imageView 에 로딩
        Glide.with(imageView).load(resId).into(imageView);
    }

    /**
     * 이미지 URL 을 ImageView 에 랜더링
     */
    @BindingAdapter({"url"})
    public static void loadBitmapImage(@NonNull final AppCompatImageView imageView,
                                       @Nullable final String imageUri) {
        if (imageUri == null) {
            return;
        }

        Glide.with(imageView).load(imageUri).into(imageView);
    }
    
    /**
     * 이미지 파일을 ImageView 에 랜더링
     */
    @BindingAdapter({"file"})
    public static void loadImageFromFile(@NonNull final AppCompatImageView imageView,
                                         @Nullable final String filePath) {
        if (filePath == null) {
            return;
        }

        Glide.with(imageView).load(new File(filePath)).into(imageView);
    }
}
