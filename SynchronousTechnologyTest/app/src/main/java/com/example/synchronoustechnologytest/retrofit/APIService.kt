package com.example.synchronoustechnologytest.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("onecall")
    fun fetchCurrentWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("exclude") exclude: String,
        @Query("appid") appId: String,
    ): Call<ResponseBody>
}