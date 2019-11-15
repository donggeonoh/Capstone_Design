package com.donggeon.honmaker.ui.food;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.ui.BaseViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("CheckResult")
public class FoodViewModel extends BaseViewModel {
    @NonNull
    private final MutableLiveData<List<Food>> foodList = new MutableLiveData<>();

    public FoodViewModel() {
        List<Food> foodList = new ArrayList<>(Arrays.asList(
                new Food("양파전",
                        "http://file.okdab.com/UserFiles/searching/recipe/119300.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=421&own="),
                new Food("소고기무국",
                        "http://file.okdab.com/UserFiles/searching/recipe/049400.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=202&own="),
                new Food("수정과",
                        "http://file.okdab.com/UserFiles/searching/recipe/039800.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=171&own="),
                new Food("미역국",
                        "http://file.okdab.com/UserFiles/searching/recipe/004700.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=21&own="),
                new Food("양파전",
                        "http://file.okdab.com/UserFiles/searching/recipe/119300.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=421&own="),
                new Food("소고기무국",
                        "http://file.okdab.com/UserFiles/searching/recipe/049400.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=202&own="),
                new Food("수정과",
                        "http://file.okdab.com/UserFiles/searching/recipe/039800.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=171&own="),
                new Food("미역국",
                        "http://file.okdab.com/UserFiles/searching/recipe/004700.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=21&own="),
                new Food("양파전",
                        "http://file.okdab.com/UserFiles/searching/recipe/119300.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=421&own="),
                new Food("양파전",
                        "http://file.okdab.com/UserFiles/searching/recipe/119300.jpg",
                        "http://www.horangi.kr/foodinfo/viewRecipe.asp?rid=421&own=")));

        this.foodList.setValue(foodList);
    }

    @NonNull
    public LiveData<List<Food>> getFoodList() {
        return foodList;
    }
}
