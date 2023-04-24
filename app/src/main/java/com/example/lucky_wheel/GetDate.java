package com.example.lucky_wheel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetDate {



    @Headers({"Content-type: application/json"})
    @GET(" ")

    Call<Location> getLocation(@Query(value = "")String location);



}
