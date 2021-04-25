package com.example.wtw_weather_app.api_model

data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)
