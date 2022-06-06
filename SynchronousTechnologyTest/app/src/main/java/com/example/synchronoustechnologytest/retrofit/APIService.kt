package com.example.synchronoustechnologytest.retrofit

import com.example.synchronoustechnologytest.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("weather")
    fun fetchCurrentWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") appId: String,
    ): Call<Forecast>
}