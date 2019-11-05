package com.donggeon.honmaker.ui.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Rational;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

import com.donggeon.honmaker.utils.FileUtil;

import java.io.File;

@SuppressLint("RestrictedApi")
public class CameraView extends TextureView {
    private static final Rational rational = new Rational(3, 4);
    private static final CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;

    private ImageCapture imageCapture;
    private Preview preview;
    private Context context;

    public CameraView(Context context) {
        super(context);
        this.context = context;
        initLayout();
        post(this::bindCameraUseCases);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLayout();
        post(this::bindCameraUseCases);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initLayout();
        post(this::bindCameraUseCases);
    }

    public void setImageCaptureButton(@NonNull final View button,
                                      @NonNull final ImageCapture.OnImageSavedListener listener) {
        button.setOnClickListener(__ ->
                imageCapture.takePicture(FileUtil.createNewFile(), listener));
    }

    private void initLayout() {
        post(() -> {
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = params.width / rational.getNumerator() * rational.getDenominator();
            requestLayout();
        });
    }

    /**
     * rational 에 맞춰 뷰 업데이트
     */
    private void bindCameraUseCases() {
        bindCameraPreview();
        bindImageCapture();

        CameraX.bindToLifecycle((LifecycleOwner) context, preview, imageCapture);
    }

    /**
     * 카메라 화면 설정 및 텍스쳐뷰 등록
     */
    private void bindCameraPreview() {
        final PreviewConfig.Builder builder = new PreviewConfig.Builder()
                .setLensFacing(lensFacing)
                .setTargetAspectRatio(rational)
                .setTargetRotation(getDisplay().getRotation());

        if (preview != null) {
            CameraX.unbind(preview);
        }

        preview = CameraPreviewBuilder.build(builder.build(), this);
    }

    /**
     * 카메라 캡쳐 화면 설정
     */
    public void bindImageCapture() {
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
}
