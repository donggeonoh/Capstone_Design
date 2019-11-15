package com.donggeon.honmaker.ui.ingredient;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    
    @SerializedName("ing_ImageURL")
    private String imageUri;
    
    @SerializedName("ing_Name")
    private String name;
    
    @SerializedName("ing_Location")
    private Place place;
    
    public Ingredient(String imageUri, String name, Place place) {
        this.imageUri = imageUri;
        this.name = name;
        this.place = place;
    }
    
    public String getImageUri() {
        return imageUri;
    }
    
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Place getPlace() {
        return place;
    }
    
    public void setPlace(Place place) {
        this.place = place;
    }
}
