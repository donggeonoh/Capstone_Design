package com.donggeon.honmaker.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityMainBinding;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
import com.donggeon.honmaker.extension.Retrofit.User;
import com.donggeon.honmaker.extension.ted.ImagePickerUtil;
import com.donggeon.honmaker.extension.ted.PermissionUtil;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.camera.CameraActivity;
import com.donggeon.honmaker.ui.food.FoodActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientActivity;
import com.donggeon.honmaker.ui.ingredient.Place;
import com.donggeon.honmaker.ui.storageActivity.StorageActivity;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class MainActivity extends BaseActivity<ActivityMainBinding> {
    
    public static String uid;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        anonymousLogin();
        initViews();
    }
    
    private void anonymousLogin() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        
        mAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                uid = mAuth.getCurrentUser().getUid();
                Log.d("Login", "Sign in anonymously : " + uid);
                
                RetrofitAPI api = RetrofitClient.getClient().create(RetrofitAPI.class);
                Call<String> call = api.login(new User(mAuth.getCurrentUser().getUid()));
            
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        Log.d("Retrofit", result);
                    }
                
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("tag", t.toString());
                    }
                });
            } else {
                Log.w("Login", "signInAnonymously:failure", task.getException());
            }
        });
    }
    
    private void initViews() {
        binding.ivCamera.setOnClickListener(__ -> checkPermission());
        binding.ivAlbum.setOnClickListener(__ -> startAlbumActivity());
        binding.ivDrawer.setOnClickListener(__ -> startStorageActivity(Place.ROOM));
        binding.ivFreeze.setOnClickListener(__ -> startStorageActivity(Place.FREEZE));
        binding.ivFridge.setOnClickListener(__ -> startStorageActivity(Place.FRIDGE));
        binding.ivRecipe.setOnClickListener(__ -> startRecipeActivity());
    }

    private void checkPermission() {
        PermissionUtil.requestPermission(this,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(result -> startCameraActivity(),
                Throwable::printStackTrace);
    }

    private void startStorageActivity(Place place) {
        startActivity(StorageActivity.getLaunchIntent(this, place));
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

    private void startRecipeActivity() {
        startActivity(new Intent(this, FoodActivity.class));
    }

}