package com.donggeon.honmaker.ui.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Rational;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

import com.donggeon.honmaker.utils.FileUtil;

import java.lang.ref.WeakReference;

@SuppressLint("RestrictedApi")
public class CameraView extends TextureView {
    // 카메라 프리뷰 비율 설정 (3:4)
    private static final Rational rational = new Rational(3, 4);
    // 카메라 렌즈 설정 (후면 카메라)
    private static final CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;

    // 카메라 화면 표시하는 객체
    private Preview preview;
    // 카메라 이미지 캡쳐, 저장하는 객체
    private ImageCapture imageCapture;

    // 뷰가 붙어있는 context 객체
    private Context context;

    // 카메라 촬영 버튼
    // (메모리 누수 방지를 위해 View 객체를 직접 들고 있지 않고 WeakReference 로 참조함)
    private WeakReference<View> captureButton;

    /**
     * 필수 생성자 3개
     */
    public CameraView(Context context) {
        super(context);
        initView(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 뷰 초기화
     */
    private void initView(@NonNull final Context context) {
        this.context = context;

        // post 는 보통 화면에 뷰가 그려지고 난 후 처리해야 될 작업이 있을 때 사용됨
        // 여기서는 뷰의 크기를 변경하기 위하여 사용했음
        post(() -> {
            // 화면에 그려진 후의 뷰의 너비(width) 를 받아와서 3:4 비율을 계산하여 높이(height) 변경
            getLayoutParams().height = getWidth() / rational.getNumerator() * rational.getDenominator();
            requestLayout(); // 뷰 다시 그리기 위해 호출
        });

        // 뷰의 크기가 변경 된 후에 실제 카메라 프리뷰를 화면에 표시함
        post(this::bindCameraUseCases);
    }

    /**
     * 카메라 프리뷰 등록
     */
    private void bindCameraUseCases() {
        bindCameraPreview(); // 카메라 프리뷰 설정
        bindImageCapture(); // 카메라 캡쳐, 저장 설정

        // CameraX 를 통해 프리뷰 객체와 캡쳐 객체를 액티비티 라이프사이클에 바인딩
        CameraX.bindToLifecycle((LifecycleOwner) context, preview, imageCapture);
    }

    /**
     * 카메라 프리뷰 설정 및 텍스쳐뷰 등록
     */
    private void bindCameraPreview() {
        final PreviewConfig.Builder builder = new PreviewConfig.Builder()
                .setLensFacing(lensFacing)  // 후면 카메라 사용 설정
                .setTargetAspectRatio(rational)  // 3:4 비율 설정
                .setTargetRotation(getDisplay().getRotation());

        if (preview != null) {
            CameraX.unbind(preview);
        }

        // 카메라 프리뷰 객체 생성
        preview = CameraPreviewBuilder.build(builder.build(), this);
    }

    /**
     * 카메라 캡쳐 화면 설정
     */
    private void bindImageCapture() {
        final ImageCaptureConfig.Builder builder = new ImageCaptureConfig.Builder()
                .setLensFacing(lensFacing)  // 후면 카메라 사용 설정
                .setTargetAspectRatio(rational)  // 3:4 비율 설정
                .setTargetRotation(getDisplay().getRotation())
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY);

        if (imageCapture != null) {
            CameraX.unbind(imageCapture);
        }

        // 이미지 캡쳐 객체 생성
        imageCapture = new ImageCapture(builder.build());
    }

    /**
     * 카메라 촬영 버튼 등록 - 액티비티에서 호출하기 때문에 public 으로 열어놓음
     */
    public void setImageCaptureButton(@NonNull final View view) {
        this.captureButton = new WeakReference<>(view);
    }

    /**
     * 카메라 촬영 후 결과를 받은 listener 등록 - 액티비티에서 호출하기 때문에 public 으로 열어놓음
     */
    public void setImageCaptureListener(@NonNull final ImageCapture.OnImageSavedListener listener) {
        // 촬영 버튼이 등록되어 있는지 먼저 확인
        if (captureButton == null || captureButton.get() == null) {
            return;
        }

        // 카메라 촬영 버튼에 리스너를 등록
        // CameraX 에서 제공하는 ImageCapture 클래스의 takePicture() 메서드에
        // 저장할 File 과 Listener 를 넘겨주면 알아서 파일에 저장해줌
        captureButton.get().setOnClickListener(__ ->
                imageCapture.takePicture(FileUtil.createNewFile(), listener));
    }
}
