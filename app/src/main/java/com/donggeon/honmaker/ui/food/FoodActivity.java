package com.donggeon.honmaker.ui.food;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.donggeon.honmaker.R;
import com.donggeon.honmaker.data.Food;
import com.donggeon.honmaker.data.FoodRating;
import com.donggeon.honmaker.data.Status;
import com.donggeon.honmaker.databinding.ActivityFoodBinding;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
import com.donggeon.honmaker.ui.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        binding.rvIncludedIngredientFood.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvIncludedIngredientFood.setAdapter(new FoodAdapter(this::startRecipeActivity, this::startRatingActivity));
        binding.rvNotIncludedIngredientFood.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvNotIncludedIngredientFood.setAdapter(new FoodAdapter(this::startRecipeActivity, this::startRatingActivity));
    }
    
    private void startRatingActivity(Food item) {
        
        View view = getLayoutInflater().inflate(R.layout.dialog_rating, null);
        AlertDialog ad = new AlertDialog.Builder(this).setView(view).create();
        
        RatingBar ratingBar = view.findViewById(R.id.rb_rating_food);
        AppCompatTextView textView = view.findViewById(R.id.tv_submit);
        
        ad.show();
        
        textView.setOnClickListener(__ -> {
    
            float rating = ratingBar.getRating();
            Log.d("dialog message", "rating : " + rating);
            Log.d("dialog message", "item : " + item.getFoodName());
            Log.d("dialog message", "item resources : " + item.getImageUrl() + ", " + item.getRecipeUrl());
            Log.d("dialog message", "uid : " + FirebaseAuth.getInstance().getCurrentUser().getUid());
    
            RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
            Call<Status> call = api.rating(new FoodRating(FirebaseAuth.getInstance().getCurrentUser().getUid(), item.getFoodName(), rating));
            
            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    
                    if(response.body() == null) {
                        Log.d("dialog message", "response is null");
                        return;
                    }
                    Status result = response.body();
                    
                    // 200 : 새로운 레시피에 대한 평점을 매겼을 때, 300 : 평점을 매긴 레시피에 대한 평점을 매겼을 때
                    switch (result.getCode()) {
                        case 200:
                            Toast.makeText(FoodActivity.this, rating + "점을 주었습니다.", Toast.LENGTH_SHORT).show();
                            break;
                            
                        case 300:
                            Toast.makeText(FoodActivity.this, rating + "점으로 갱신하였습니다.", Toast.LENGTH_SHORT).show();
                            break;
                            
                            default:
                                Toast.makeText(FoodActivity.this, "알 수 없는 오류입니다.", Toast.LENGTH_SHORT).show();
                    }
                    
                    ad.dismiss();
                }
        
                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    ad.dismiss();
                }
            });
        });
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