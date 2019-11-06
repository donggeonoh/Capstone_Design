package com.donggeon.honmaker.extension.ted;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import gun0912.tedimagepicker.builder.TedRxImagePicker;
import io.reactivex.Single;

/**
 * TedImagePicker 를 호출해주는 클래스
 */
public class ImagePickerUtil {

    /**
     * TedImagePicker 로 앨범을 열 때 동영상은 제외, 이미지 파일만 받아오고
     * 카메라 버튼은 사용하지 않도록 설정
     */
    public static Single<Uri> startImagePicker(@NonNull final Context context) {
        return TedRxImagePicker.with(context)
                .image()
                .showCameraTile(false)
                .start();
    }
}
