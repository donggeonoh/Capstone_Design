package com.donggeon.honmaker.ui.food;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.data.Food;
import com.donggeon.honmaker.data.FoodList;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
import com.donggeon.honmaker.ui.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class FoodViewModel extends BaseViewModel {
    
    @NonNull
    private final MutableLiveData<List<Food>> foodList = new MutableLiveData<>();
    
    @NonNull
    private final MutableLiveData<List<Food>> nonFoodList = new MutableLiveData<>();
    
    public FoodViewModel() {
    
        RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
        Call<FoodList> call = api.recommend(FirebaseAuth.getInstance().getCurrentUser().getUid());
    
        call.enqueue(new Callback<FoodList>() {
            @Override
            public void onResponse(Call<FoodList> call, Response<FoodList> response) {
                
                if(response.body() == null) {
                    return;
                }
                
                for (Food data : response.body().getContained()) {
                    Log.d("FoodActivity", "name : " + data.getFoodName() + " uri : " + data.getImageUrl() + ", " + data.getRecipeUrl());
                }
                
                foodList.setValue(response.body().getContained());
                nonFoodList.setValue(response.body().getUncontained());
            }
        
            @Override
            public void onFailure(Call<FoodList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @NonNull
    public LiveData<List<Food>> getFoodList() {
        return foodList;
    }
    
    @NonNull
    public LiveData<List<Food>> getNonFoodList() {
        return nonFoodList;
    }
}
