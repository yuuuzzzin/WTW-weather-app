package com.example.wtw_weather_app.api_model

data class WeatherApi(
    var lat: Double,
    var lon: Double,
    var timezone: String,
    var current: CurrentAndHourly?,
    var daily: ArrayList<Daily>?,
    var hourly: ArrayList<CurrentAndHourly>?
)
