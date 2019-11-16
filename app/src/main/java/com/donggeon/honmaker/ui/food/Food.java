package com.donggeon.honmaker.ui.food;

import java.util.Objects;

public class Food {
    
    private String foodName;
    private String imageUrl;
    private String recipeUrl;
    
    public Food() {
    }

    public Food(String foodName, String imageUrl, String recipeUrl) {
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.recipeUrl = recipeUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(foodName, food.foodName) &&
                Objects.equals(imageUrl, food.imageUrl) &&
                Objects.equals(recipeUrl, food.recipeUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodName, imageUrl, recipeUrl);
    }
}
