package com.donggeon.honmaker.data;

import com.donggeon.honmaker.ui.ingredient.Place;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Ingredient {
    
    @SerializedName("ing_Location")
    private Place place;
    
    @SerializedName("ing_Name")
    private String name;
    
    @SerializedName("ing_URL")
    private String imageUri;
    
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return getImageUri().equals(that.getImageUri()) &&
                getName().equals(that.getName()) &&
                getPlace() == that.getPlace();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getImageUri(), getName(), getPlace());
    }
}
