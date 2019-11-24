package com.donggeon.honmaker.extension.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://52.79.234.234:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
