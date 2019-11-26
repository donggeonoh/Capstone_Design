package com.donggeon.honmaker.ui.storageActivity;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.data.Ingredient;
import com.donggeon.honmaker.extension.Retrofit.RetrofitAPI;
import com.donggeon.honmaker.extension.Retrofit.RetrofitClient;
import com.donggeon.honmaker.ui.BaseViewModel;
import com.donggeon.honmaker.ui.ingredient.Place;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class StorageViewModel extends BaseViewModel {
    @NonNull
    private final MutableLiveData<List<Ingredient>> ingredientList = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Place> place = new MutableLiveData<>();

    public StorageViewModel() {
    
    }

    public LiveData<List<Ingredient>> getIngredientList() {
        return ingredientList;
    }

    public void setPlace(Place place) {
        this.place.setValue(place);
        loadIngredientList();
    }

    public void loadIngredientList() {
        Log.d("Storage Activity", FirebaseAuth.getInstance().getCurrentUser().getUid());
        
        RetrofitAPI api = RetrofitClient.retrofit.create(RetrofitAPI.class);
        Call<List<Ingredient>> call = api.getAllIngredient(FirebaseAuth.getInstance().getCurrentUser().getUid());
        
        call.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                
                if(response.body() == null) {
                    Log.d("Storage Activity", "Data is null");
                    return;
                }
            
                ArrayList<Ingredient> values = (ArrayList<Ingredient>) response.body();
                ArrayList<Ingredient> list = new ArrayList<>();
            
                for (Ingredient value : values) {
                    if (value.getPlace() == place.getValue()) {
                        list.add(value);
                    }
                    Log.d("Storage Activity", "Name : " + value.getName() + " URI : " + value.getImageUri() + " PLACE : " + value.getPlace());
                }
                ingredientList.setValue(list);
            }
        
            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                Log.d("Storage Activity", "FAIL");
                t.printStackTrace();
            }
        });
    }
}
