package com.example.wtw_weather_app.api_model

data class CurrentAndHourly(
    var dt: Long,
    var temp: Double,
    var feels_like: Double,
    var humidity: Int,
    var clouds: Int,
    var rain: Int,
    var snow: Int,
    var weather: ArrayList<Weather>
)
