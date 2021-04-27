package com.example.wtw_weather_app.api_model

data class Daily(
    var dt: Long,
    var temp: DailyTemp,
    var humidity: Int,
    var clouds: Int,
    var weather: ArrayList<Weather>
)
