package com.donggeon.honmaker.ui.ingredient;

import com.donggeon.honmaker.R;

public enum Ingredient {
    HAM(R.drawable.ic_ham, "햄"),
    TUNA(R.drawable.ic_tuna, "참치"),
    SAUSAGE(R.drawable.ic_sausage, "소세지"),
    SALT(R.drawable.ic_salt, "소금"),
    CHEESE(R.drawable.ic_cheese, "치즈");

    private int imageResId;
    private String name;

    Ingredient(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }
}
