package com.donggeon.honmaker.extension.Retrofit;

import com.donggeon.honmaker.data.FoodList;
import com.donggeon.honmaker.data.FoodRating;
import com.donggeon.honmaker.data.Ingredient;
import com.donggeon.honmaker.data.Text;
import com.donggeon.honmaker.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    
    @POST("/")
    Call<String> login(@Body User user);
    
    @POST("/ingredients/put")
    Call<List<Ingredient>> ingredient(@Body Text text);
    
    @POST("/rating/put")
    Call<String> rating(@Body FoodRating data);
    
    @GET("/recommend?")
    Call<FoodList> recommend(@Query("UID") User user);
    
    @GET("/ingredients/get")
    Call<List<Ingredient>> getAllIngredient(@Query("UID") String uid);
    
    @POST("/ingredients/get")
    Call<List<Ingredient>> addIngredient(@Query("UID") String uid, Ingredient ingredient);
    
    @GET("/ingredients/get")
    Call<List<Ingredient>> deleteIngredient(@Query("UID") String uid);
}