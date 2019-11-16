package com.donggeon.honmaker.extension.Retrofit;

import com.donggeon.honmaker.ui.ingredient.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    
    @POST("/")
    Call<String> login(@Body User user);
    
    @POST("/ingredient")
    Call<List<Ingredient>> ingredient(@Body Text text);
    
    @GET("/recipe")
    Call<String> recipe(@Body String str);
    
    @POST("/rating/put")
    Call<String> rating(@Body FoodRating data);
}