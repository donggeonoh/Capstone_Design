package com.donggeon.honmaker.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodList {
    
    @SerializedName("contained")
    private List<Food> contained;
    
    @SerializedName("uncontained")
    private List<Food> uncontained;
    
    public FoodList(List<Food> contained, List<Food> uncontained) {
        this.contained = contained;
        this.uncontained = uncontained;
    }
    
    public List<Food> getContained() {
        return contained;
    }
    
    public void setContained(List<Food> contained) {
        this.contained = contained;
    }
    
    public List<Food> getUncontained() {
        return uncontained;
    }
    
    public void setUncontained(List<Food> uncontained) {
        this.uncontained = uncontained;
    }
}
