package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

public class DeleteIngredient {
    
    @SerializedName("code")
    String code;
    
    Ingredient ingredient;
    
    public DeleteIngredient(String code, Ingredient ingredient) {
        this.code = code;
        this.ingredient = ingredient;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
