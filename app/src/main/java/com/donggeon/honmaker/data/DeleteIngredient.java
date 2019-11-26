package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

public class DeleteIngredient {
    
    @SerializedName("code")
    private String code;
    
    public DeleteIngredient(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
}
