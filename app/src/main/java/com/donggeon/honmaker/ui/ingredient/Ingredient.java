package com.donggeon.honmaker.ui.ingredient;

public class Ingredient {
    private int imageResId;
    private int name;

    public Ingredient(int imageResId, int name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
