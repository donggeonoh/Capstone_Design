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
import com.donggeon.honmaker.extension.ted.ImagePickerUtil;
import com.donggeon.honmaker.extension.ted.PermissionUtil;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.camera.CameraActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientActivity;
import com.donggeon.honmaker.ui.storageActivity.StorageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("CheckResult")
public class MainActivity extends BaseActivity<ActivityMainBinding> {
    
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Retrofit mRetrofit;
    private RetrofitAPI mRetrofitAPI;
    private Call<String> mCallList;
    private Callback<String> mRetrofitCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String result = response.body();
            Log.d("Retrofit", result);
        }
    
        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
        }
    };
    
    private void callUid(String uid) {
        mCallList = mRetrofitAPI.login(uid);
        mCallList.enqueue(mRetrofitCallback);
    }
    
    private void setRetrofit() {
        mRetrofit = new Retrofit.Builder().baseUrl("http://52.79.234.234").addConverterFactory(GsonConverterFactory.create()).build();
        mRetrofitAPI = mRetrofit.create(RetrofitAPI.class);
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetrofit();
    
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("Login", "Sign in anonymously : " + mAuth.getCurrentUser().getUid());
                    callUid(mAuth.getCurrentUser().getUid());
                } else {
                    Log.w("Login", "signInAnonymously:failure", task.getException());
                }
            
            }
        });
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