package com.example.wtw_weather_app

import android.telecom.Call
import com.example.wtw_weather_app.api_model.WeatherApi
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/onecall")
    fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String
    ): Call<WeatherApi>
}