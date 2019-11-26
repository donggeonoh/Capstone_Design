package com.donggeon.honmaker.extension.Retrofit;

import com.donggeon.honmaker.data.AddIngredient;
import com.donggeon.honmaker.data.FoodList;
import com.donggeon.honmaker.data.FoodRating;
import com.donggeon.honmaker.data.Ingredient;
import com.donggeon.honmaker.data.Status;
import com.donggeon.honmaker.data.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    
    @FormUrlEncoded
    @POST("/")
    Call<String> login(@Field("UID") String UID);
    
    @POST("/ingredients/put")
    Call<List<Ingredient>> ingredient(@Body Text text);
    
    @POST("/rating/put")
    Call<Status> rating(@Body FoodRating data);
    
    @GET("/recommend")
    Call<FoodList> recommend(@Query("UID") String uid);
    
    @GET("/ingredients/get")
    Call<List<Ingredient>> getAllIngredient(@Query("UID") String uid);
    
    @FormUrlEncoded
    @POST("user/ingredients/put")
    Call<AddIngredient> addIngredient(@Field("UID") String UID, @Field("ing_Name") String ing_Name);
    
    @FormUrlEncoded
    @POST("user/ingredients/delete")
    Call<String> deleteIngredient(@Field("UID") String UID, @Field("ing_Name") String ing_Name);
}