package com.donggeon.honmaker.ui.ingredient;

import com.donggeon.honmaker.R;

public enum Ingredient {
    HAM(R.drawable.ic_ham, "햄"),
    TUNA(R.drawable.ic_tuna, "참치"),
    CHEEZE(R.drawable.ic_cheese, "치즈");

    public static final int COUNT = 3;

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
