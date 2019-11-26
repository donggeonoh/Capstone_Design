package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

public class AddIngredient {
    
    @SerializedName("code")
    private int code;
    
    private Ingredient ingredient;
    
    public AddIngredient(int code, Ingredient ingredient) {
        this.code = code;
        this.ingredient = ingredient;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
