package com.donggeon.honmaker.ui.camera;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityCameraBinding;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientActivity;

import java.io.File;

public class CameraActivity extends BaseActivity<ActivityCameraBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        binding.viewFinder.setImageCaptureButton(binding.ivCamera);
        binding.viewFinder.setImageCaptureListener(
                new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        startImageActivity(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError,
                                        @NonNull String message, @Nullable Throwable cause) {

                    }
                });
    }

    private void startImageActivity(@NonNull final String path) {
        startActivity(IngredientActivity.getLaunchIntent(this, path));
    }
}
