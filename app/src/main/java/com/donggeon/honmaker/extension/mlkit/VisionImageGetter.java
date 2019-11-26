package com.donggeon.honmaker.extension.mlkit;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;
import java.io.IOException;

/**
 * Firebase MLKit 에 사용되는 FirebaseVisionImage 객체를 만들어주는 클래스
 */
public class VisionImageGetter {

    /**
     * file 로부터 FirebaseVisionImage 를 생성
     *
     * @param context  파일을 받아오기 위해 필요한 context
     * @param filePath 파일의 경로
     */
    public static FirebaseVisionImage getImageFromFilePath(@NonNull final Context context,
                                                           @NonNull final String filePath) {
        // filePath 로부터 Uri 객체를 생성해서 아래있는 getImageFromUri() 메서드로 위임
        return getImageFromUri(context, Uri.fromFile(new File(filePath)));
    }

    /**
     * file 로부터 FirebaseVisionImage 를 생성
     * <p>
     * Uri 는 그냥 파일 경로를 담은 객체라고 생각하면 됨
     *
     * @param context 파일을 받아오기 위해 필요한 context
     * @param uri     앨범에서 이미지 선택하면 얻을 수 있는 uri
     */
    public static FirebaseVisionImage getImageFromUri(@NonNull final Context context,
                                                      @NonNull final Uri uri) {
        try {
            return FirebaseVisionImage.fromFilePath(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    /**
     * Bitmap 으로부터 FirebaseVisionImage 를 생성
     */
    public static FirebaseVisionImage getImagefromBitmap(@NonNull final Bitmap bitmap) {
        return FirebaseVisionImage.fromBitmap(bitmap);
    }

    /**
     * Image 로부터 FirebaseVisionImage 를 생성
     * <p>
     * CameraX 프리뷰에서 실시간으로 이미지 분석이 필요할 때 사용됨
     */
    public static FirebaseVisionImage getImageFromImage(@NonNull final Image mediaImage,
                                                        final int rotation) {

        return FirebaseVisionImage.fromMediaImage(mediaImage, rotation);
    }
}
