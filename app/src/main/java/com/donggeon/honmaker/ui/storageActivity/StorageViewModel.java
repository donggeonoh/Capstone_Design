package com.donggeon.honmaker.ui.storageActivity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.donggeon.honmaker.ui.BaseViewModel;
import com.donggeon.honmaker.ui.ingredient.Ingredient;
import com.donggeon.honmaker.ui.ingredient.Place;

import java.util.ArrayList;
import java.util.List;

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

    private void loadIngredientList() {
        Ingredient[] values = Ingredient.values();
        ArrayList<Ingredient> list = new ArrayList<>();
        for (Ingredient value : values) {
            if (value.getPlace() == place.getValue()) {
                list.add(value);
            }
        }
        ingredientList.setValue(list);
    }
}