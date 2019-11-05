package com.donggeon.honmaker.ui.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Rational;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

import com.donggeon.honmaker.utils.FileUtil;

import java.io.File;
import java.lang.ref.WeakReference;

@SuppressLint("RestrictedApi")
public class CameraView extends TextureView {
    private static final Rational ratio = new Rational(3, 4);
    private static final CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;

    private ImageCapture imageCapture;
    private Preview preview;
    private Context context;

    private WeakReference<View> imageCaptureButtonRef;
    private ImageCapture.OnImageSavedListener imageCaptureListener;

    public CameraView(Context context) {
        super(context);
        this.context = context;
        initLayout(ratio, lensFacing);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLayout(ratio, lensFacing);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initLayout(ratio, lensFacing);
    }

    private void setImageCaptureButton(@NonNull final ImageCapture.Metadata metadata) {
        View view = imageCaptureButtonRef.get();
        if (view == null) {
            return;
        }
        view.setOnClickListener(__ ->
                imageCapture.takePicture(FileUtil.createNewFile(), imageCaptureListener, metadata));
    }

    public void setImageCaptureButton(@NonNull final View imageCaptureButton) {
        this.imageCaptureButtonRef = new WeakReference<>(imageCaptureButton);
    }

    public void setImageCaptureListener(@NonNull final ImageCapture.OnImageSavedListener imageCaptureListener) {
        this.imageCaptureListener = imageCaptureListener;
    }

    private void initLayout(@NonNull final Rational rational,
                            @NonNull final CameraX.LensFacing lensFacing) {
        post(() -> {
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = params.width * rational.getNumerator() / rational.getDenominator();
            requestLayout();
        });
        post(() -> bindCameraUseCases(rational, lensFacing));
    }

    /**
     * rational 에 맞춰 뷰 업데이트
     */
    private void bindCameraUseCases(@NonNull final Rational rational,
                                    @NonNull final CameraX.LensFacing lensFacing) {
        bindCameraPreview(rational, lensFacing);
        bindImageCapture(rational, lensFacing);

        // 전면 카메라 좌우 반전
        ImageCapture.Metadata metadata = new ImageCapture.Metadata();
        metadata.isReversedHorizontal = lensFacing == CameraX.LensFacing.FRONT;
        setImageCaptureButton(metadata);

        CameraX.bindToLifecycle((LifecycleOwner) context, preview, imageCapture);
    }

    /**
     * 카메라 화면 설정 및 텍스쳐뷰 등록
     */
    private void bindCameraPreview(@NonNull final Rational rational,
                                   @NonNull final CameraX.LensFacing lensFacing) {

        final PreviewConfig.Builder builder = new PreviewConfig.Builder()
                .setLensFacing(lensFacing)
                .setTargetAspectRatio(rational)
                .setTargetRotation(getDisplay().getRotation());

        if (preview != null) {
            CameraX.unbind(preview);
        }

        preview = AutoFitPreviewBuilder.build(builder.build(), this);
    }

    /**
     * 카메라 캡쳐 화면 설정
     */
    public void bindImageCapture(@NonNull final Rational rational,
                                 @NonNull final CameraX.LensFacing lensFacing) {
        final ImageCaptureConfig.Builder builder = new ImageCaptureConfig.Builder()
                .setLensFacing(lensFacing)
                .setTargetAspectRatio(rational)
                .setTargetRotation(getDisplay().getRotation())
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY);

        if (imageCapture != null) {
            CameraX.unbind(imageCapture);
        }

        imageCapture = new ImageCapture(builder.build());
    }


    public interface onSaveListener {
        void onImageSaved(File file);
    }

    public interface onErrorListener {
        void onError(ImageCapture.ImageCaptureError imageCaptureError,
                     String message,
                     Throwable cause);
    }
}
