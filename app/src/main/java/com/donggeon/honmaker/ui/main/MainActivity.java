package com.donggeon.honmaker.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.data.AddIngredient;
import com.donggeon.honmaker.databinding.ActivityMainBinding;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
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
        
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                
                Log.d("Login111", "Sign in anonymously : " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                
                RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
                Call<String> call = api.login(FirebaseAuth.getInstance().getCurrentUser().getUid());
                
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        
                        String result = response.body();
                        if(result == null) {
                            return;
                        }
                        
                        Log.d("Retrofit", result);
                        Log.d("Login", "Sign in anonymously : " + FirebaseAuth.getInstance().getCurrentUser().getUid());
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
        binding.ivDrawer.setOnClickListener(__ -> startStorageActivity(Place.상온));
        binding.ivFreeze.setOnClickListener(__ -> startStorageActivity(Place.냉동));
        binding.ivFridge.setOnClickListener(__ -> startStorageActivity(Place.냉장));
        binding.ivRecipe.setOnClickListener(__ -> startRecipeActivity());
        binding.ivAddIngredient.setOnClickListener(__ -> startAddIngredientDial());
    }
    
    private void startAddIngredientDial() {
        
        View view = getLayoutInflater().inflate(R.layout.dialog_add_ingredient, null);
        AlertDialog ad = new AlertDialog.Builder(this).setView(view).create();
        
        AppCompatEditText ingredientEditText = view.findViewById(R.id.et_ingredient);
        AppCompatTextView submitTextView = view.findViewById(R.id.tv_submit);
        
        ad.show();
        
        submitTextView.setOnClickListener(__ -> {
            RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
            Call<AddIngredient> call = api.addIngredient(FirebaseAuth.getInstance().getCurrentUser().getUid(), ingredientEditText.getText().toString());
            
            call.enqueue(new Callback<AddIngredient>() {
                @Override
                public void onResponse(Call<AddIngredient> call, Response<AddIngredient> response) {
                    
                    AddIngredient result = response.body();
                    
                    if (result.getCode() == 300 || result.getCode() == 400) {
                        Toast.makeText(MainActivity.this, "재료가 없거나 이미 있습니다!", Toast.LENGTH_SHORT).show();
                        Log.d("Add ingredient", "code : " + result.getCode() + "");
                        return;
                    }
                    
                    Toast.makeText(MainActivity.this, "재료가 추가되었습니다!", Toast.LENGTH_SHORT).show();
                    ad.dismiss();
                }
                
                @Override
                public void onFailure(Call<AddIngredient> call, Throwable t) {
                    Log.e("add ingredient dialog", t.getMessage());
                }
            });
        });
        
    }
    
    private void startStorageActivity(Place place) {
        startActivity(StorageActivity.getLaunchIntent(getApplicationContext(), place));
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
    
    private void checkPermission() {
        PermissionUtil.requestPermission(this,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(result -> startCameraActivity(),
                Throwable::printStackTrace);
    }
}