package com.donggeon.honmaker.extension.databinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture;
import androidx.databinding.BindingAdapter;

import com.donggeon.honmaker.ui.camera.CameraView;

import java.io.File;

public class CameraViewAdapters {

    @BindingAdapter(value = {"captureId"})
    public static void setImageCaptureButton(@NonNull final CameraView cameraView,
                                             final int viewId) {

        cameraView.setImageCaptureButton(cameraView.getRootView().findViewById(viewId));
    }

    @BindingAdapter(value = {"onImageSave", "onSaveError"})
    public static void setImageCaptureListener(@NonNull final CameraView cameraView,
                                               @Nullable final CameraView.onSaveListener saveListener,
                                               @Nullable final CameraView.onErrorListener errorListener) {

        cameraView.setImageCaptureListener(
                new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        if (saveListener != null) {
                            saveListener.onImageSaved(file);
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError,
                                        @NonNull String message,
                                        @Nullable Throwable cause) {
                        if (errorListener != null) {
                            errorListener.onError(imageCaptureError, message, cause);
                        }
                    }
                });
    }
}
