package com.donggeon.honmaker.ui.ingredient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityIngredientBinding;
import com.donggeon.honmaker.ui.BaseActivity;

public class IngredientActivity extends BaseActivity<ActivityIngredientBinding> {
    private static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";

    private String filePath;

    public static Intent getLaunchIntent(@NonNull final Context context,
                                         @NonNull final String path) {
        Intent intent = new Intent(context, IngredientActivity.class);
        intent.putExtra(EXTRA_FILE_PATH, path);
        return intent;
    }

    private void getExtras(@NonNull final Intent intent) {
        filePath = intent.getStringExtra(EXTRA_FILE_PATH);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ingredient;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras(getIntent());
        initViewModel();
        initView();
    }

    private void initView() {
        binding.rvIngredient.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvIngredient.setAdapter(new IngredientAdapter());
    }

    private void initViewModel() {
        binding.setVm(ViewModelProviders.of(this).get(IngredientViewModel.class));
        binding.getVm().init(filePath);
    }
}
