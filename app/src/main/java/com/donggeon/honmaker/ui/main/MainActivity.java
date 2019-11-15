package com.donggeon.honmaker.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityMainBinding;
import com.donggeon.honmaker.extension.ted.ImagePickerUtil;
import com.donggeon.honmaker.extension.ted.PermissionUtil;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.camera.CameraActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientActivity;
import com.donggeon.honmaker.ui.storageActivity.StorageActivity;

@SuppressLint("CheckResult")
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        binding.ivCamera.setOnClickListener(__ -> checkPermission());
        binding.ivAlbum.setOnClickListener(__ -> startAlbumActivity());
        binding.ivDrawer.setOnClickListener(__ -> startDrawerActivity());
    }
    
    private void startDrawerActivity() {
        startActivity(new Intent(this, StorageActivity.class));
    }
    
    private void checkPermission() {
        PermissionUtil.requestPermission(this,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(result -> startCameraActivity(),
                Throwable::printStackTrace);
    }

    private void startAlbumActivity() {
        ImagePickerUtil.startImagePicker(this)
                .subscribe(uri -> startImageActivity(uri.getPath()),
                        Throwable::printStackTrace);
    }

    private void startImageActivity(@NonNull final String filePath) {
        startActivity(IngredientActivity.getLaunchIntent(this, filePath));
    }

    private void startCameraActivity() {
        startActivity(new Intent(this, CameraActivity.class));
    }

}