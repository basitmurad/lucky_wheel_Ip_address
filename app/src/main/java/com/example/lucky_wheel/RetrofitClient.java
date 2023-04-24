package com.example.lucky_wheel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static Retrofit retrofit;
    private static final String BASE_URL = "http://ip-api.com/json/";

public static Retrofit getRetrofitObject()
{



    if (retrofit == null) {
        Gson gsonObject  = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)

                .addConverterFactory(GsonConverterFactory.create(gsonObject))
                .build();
    }
    return retrofit;
}
}
