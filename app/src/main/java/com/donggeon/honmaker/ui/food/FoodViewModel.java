package com.donggeon.honmaker.ui.food;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("CheckResult")
public class FoodViewModel extends BaseViewModel {
    @NonNull
    private final MutableLiveData<List<Food>> foodList = new MutableLiveData<>();

    public FoodViewModel() {
        List<Food> foodList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            foodList.add(new Food(
                    "양파전",
                    "http://file.okdab.com/UserFiles/searching/recipe/119300.jpg",
                    "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=421&own="));
        }
        this.foodList.setValue(foodList);
    }

    @NonNull
    public LiveData<List<Food>> getFoodList() {
        return foodList;
    }
}
