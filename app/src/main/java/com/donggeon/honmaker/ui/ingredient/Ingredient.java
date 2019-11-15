package com.donggeon.honmaker.ui.ingredient;

import com.donggeon.honmaker.R;

public enum Ingredient {
    HAM(R.drawable.ic_ham, "햄", 1),
    TUNA(R.drawable.ic_tuna, "참치", 1),
    SAUSAGE(R.drawable.ic_sausage, "소세지", 2),
    SALT(R.drawable.ic_salt, "소금", 1),
    CHEESE(R.drawable.ic_cheese, "치즈", 2);

    private int imageResId;
    private String name;
    private int place;

    Ingredient(int imageResId, String name, int place) {
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
}
