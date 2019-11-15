package com.donggeon.honmaker.ui.storageActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityStorageBinding;
import com.donggeon.honmaker.ui.BaseActivity;
import com.donggeon.honmaker.ui.ingredient.IngredientAdapter;
import com.donggeon.honmaker.ui.ingredient.Place;

public class StorageActivity extends BaseActivity<ActivityStorageBinding> {

    private static final String EXTRA_PLACE = "EXTRA_PLACE";
    private Place place;

    public static Intent getLaunchIntent(@NonNull Context context,
                                         @NonNull Place place) {
        Intent intent = new Intent(context, StorageActivity.class);
        intent.putExtra(EXTRA_PLACE, place);
        return intent;
    }

    private void getIntentExtras(@NonNull Intent intent) {
        Bundle args = intent.getExtras();
        if (args != null) {
            place = (Place) args.getSerializable(EXTRA_PLACE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_storage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentExtras(getIntent());
        initViewModels();
        initViews();
    }

    private void initViewModels() {
        binding.setVm(ViewModelProviders.of(this).get(StorageViewModel.class));
        binding.getVm().setPlace(place);
    }

    private void initViews() {
        binding.rvIngredient.setLayoutManager(new GridLayoutManager(this, 4));
        binding.rvIngredient.setAdapter(new IngredientAdapter());
    }
}