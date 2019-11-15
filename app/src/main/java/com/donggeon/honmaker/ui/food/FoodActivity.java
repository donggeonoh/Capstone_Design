package com.donggeon.honmaker.ui.food;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.databinding.ActivityFoodBinding;
import com.donggeon.honmaker.ui.BaseActivity;

/**
 * TODO : @
 */
public class FoodActivity extends BaseActivity<ActivityFoodBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_food;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModels();
        initViews();
    }

    private void initViewModels() {
        binding.setVm(ViewModelProviders.of(this).get(FoodViewModel.class));
    }

    private void initViews() {
        binding.rvFood.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvFood.setAdapter(new FoodAdapter(this::startRecipeActivity));
    }

    private void startRecipeActivity(@NonNull Food item) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(getResources().getColor(R.color.colorPrimary))
                .setShowTitle(true)
                .build();

        customTabsIntent.launchUrl(this, Uri.parse(item.getRecipeUrl()));
    }
}
