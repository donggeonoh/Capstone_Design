package com.donggeon.honmaker.ui.camera;

import android.os.Bundle;
import android.view.ViewGroup;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    private void initViews() {
        binding.viewFinder.post(() -> {
            ViewGroup.LayoutParams params = binding.viewFinder.getLayoutParams();
            params.height = binding.viewFinder.getWidth() / 3 * 4;
            binding.viewFinder.requestLayout();
        });

        binding.viewFinder.setImageCaptureButton(binding.ivCamera,
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

    private void startImageActivity(String path) {
        startActivity(IngredientActivity.getLaunchIntent(this, path));
    }
}
