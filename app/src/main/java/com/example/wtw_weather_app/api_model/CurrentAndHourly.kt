package com.example.wtw_weather_app.api_model

data class CurrentAndHourly(
    var dt: Long,
    var temp: Double,
    var feels_like: Double,
    var humidity: Double,
    var clouds: Int,
    var rain: Double,
    var snow: Double,
    var weather: ArrayList<Weather>
)
