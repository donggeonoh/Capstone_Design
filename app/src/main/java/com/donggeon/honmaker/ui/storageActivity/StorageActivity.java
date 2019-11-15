package com.donggeon.honmaker.ui.storageActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityStorageBinding;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientAdapter;

public class StorageActivity extends BaseActivity<ActivityStorageBinding> {
    
    private int span = 4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initViews();
    }
    
    private void initViews() {
        binding.rvIngredient.setLayoutManager(new GridLayoutManager(this, span));
        binding.rvIngredient.setAdapter(new IngredientAdapter());
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_storage;
    }
}