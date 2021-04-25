package com.example.wtw_weather_app.api_model

data class Daily(
    var dt: Long,
    var temp: DailyTemp,
    var feels_like: Double,
    var humidity: Double,
    var clouds: Int,
    var rain: Double,
    var snow: Double,
    var weather: ArrayList<Weather>
)
