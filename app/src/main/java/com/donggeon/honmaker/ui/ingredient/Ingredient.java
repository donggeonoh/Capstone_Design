package com.donggeon.honmaker.ui.ingredient;

import com.donggeon.honmaker.R;

public enum Ingredient {
    HAM(R.drawable.ic_ham, "햄", Place.FRIDGE),
    TUNA(R.drawable.ic_tuna, "참치", Place.FRIDGE),
    SAUSAGE(R.drawable.ic_sausage, "소세지", Place.FREEZE),
    SALT(R.drawable.ic_salt, "소금", Place.ROOM),
    CHEESE(R.drawable.ic_cheese, "치즈", Place.FRIDGE);

    private int imageResId;
    private String name;
    private Place place;

    Ingredient(int imageResId, String name, Place place) {
        this.imageResId = imageResId;
        this.name = name;
        this.place = place;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }
}
