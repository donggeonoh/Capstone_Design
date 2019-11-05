package com.donggeon.honmaker.extension.mlkit;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class VisionImageGetter {

    public static FirebaseVisionImage getImageFromUri(@NonNull final Image mediaImage,
                                                      final int rotation) {

        return FirebaseVisionImage.fromMediaImage(mediaImage, rotation);
    }

    public static FirebaseVisionImage getImageFromFilePath(@NonNull final Context context,
                                                           @NonNull final String filePath) {
        return getImageFromUri(context, Uri.fromFile(new File(filePath)));
    }

    public static FirebaseVisionImage getImageFromUri(@NonNull final Context context,
                                                      @NonNull final Uri uri) {
        try {
            return FirebaseVisionImage.fromFilePath(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static FirebaseVisionImage getImagefromByteBuffer(@NonNull final ByteBuffer buffer,
                                                             @NonNull final FirebaseVisionImageMetadata metadata) {
        return FirebaseVisionImage.fromByteBuffer(buffer, metadata);
    }

    public static FirebaseVisionImage getImagefromBitmap(@NonNull final Bitmap bitmap) {
        return FirebaseVisionImage.fromBitmap(bitmap);
    }


//    FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
//            .setWidth(480)   // 480x360 is typically sufficient for
//            .setHeight(360)  // image recognition
//            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
//            .setRotation(rotation)
//            .build();
}
