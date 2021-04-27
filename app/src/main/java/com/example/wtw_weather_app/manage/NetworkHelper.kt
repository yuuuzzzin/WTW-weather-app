package com.example.wtw_weather_app.manage

import android.content.Context
import com.example.wtw_weather_app.R
import com.example.wtw_weather_app.WeatherApiService
import com.example.wtw_weather_app.api_model.CurrentAndHourly
import com.example.wtw_weather_app.api_model.Daily
import com.example.wtw_weather_app.api_model.WeatherApi
import com.example.wtw_weather_app.database.CurrentEntity
import com.example.wtw_weather_app.database.DailyEntity
import com.example.wtw_weather_app.database.HourlyEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkHelper(context: Context) {
    val context: Context
    val databaseManager: DatabaseManager
    var retrofit: Retrofit
    var weatherApiService: WeatherApiService

    init {
        this.context = context
        this.retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.weatherApiService = retrofit.create(WeatherApiService::class.java)
        this.databaseManager = DatabaseManager.getInstance(context)
    }

    fun requestHourlyWeatherApiService(
        lat: String,
        lon: String,
        hourlyData: (ArrayList<CurrentAndHourly>) -> Unit
    ) {
        weatherApiService.getWeather(lat, lon, context.getString(R.string.app_id), "metric")
            ?.enqueue(object : Callback<WeatherApi> {
                override fun onFailure(call: Call<WeatherApi>, t: Throwable) {
                    println("시간별 날씨 응답 실패 : $t")
                }

                override fun onResponse(call: Call<WeatherApi>, response: Response<WeatherApi>) {
                    println("시간별 날씨 응답 성공 : ${response.body().toString()}")

                    response.body()?.let {
                        hourlyData(it.hourly!!)
                        for (i in 0..23) {
                            it.hourly!![i].let{
                                HourlyEntity(i+101,
                                    it.dt,
                                    it.temp,
                                    it.feels_like,
                                    it.humidity,
                                    it.clouds,
                                    it.weather[0].id,
                                    it.weather[0].main,
                                    it.weather[0].description,
                                    it.weather[0].icon
                                ).let { entity ->
                                    GlobalScope.launch {
                                        if (databaseManager.hourlyDao().getHourly().size >= 24)
                                            databaseManager.hourlyDao().updateHourly(entity)
                                        else
                                            databaseManager.hourlyDao().insertHourly(entity)
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }

    fun requestCurrentWeatherApiService(
        lat: String,
        lon: String,
        currentData: (CurrentAndHourly) -> Unit
    ) {
        weatherApiService.getWeather(lat, lon, context.getString(R.string.app_id), "metric")
            ?.enqueue(object : Callback<WeatherApi> {
                override fun onFailure(call: Call<WeatherApi>, t: Throwable) {
                    println("현재 날씨 응답 실패 : $t")
                }

                override fun onResponse(call: Call<WeatherApi>, response: Response<WeatherApi>) {
                    println("현재 날씨 응답 성공 : ${response.body().toString()}")

                    response.body()?.let {
                        currentData(it.current!!)
                        CurrentEntity(101,
                            it.current!!.dt,
                            it.current!!.temp,
                            it.current!!.feels_like,
                            it.current!!.humidity,
                            it.current!!.clouds,
                            it.current!!.weather[0].id,
                            it.current!!.weather[0].main,
                            it.current!!.weather[0].description,
                            it.current!!.weather[0].icon
                        ).let {
                            GlobalScope.launch {
                                if (databaseManager.currentDao().getCurrent().size > 0)
                                    databaseManager.currentDao().updateCurrent(it)
                                else
                                    databaseManager.currentDao().insertCurrent(it)
                            }
                        }
                    }
                }
            })
    }

    fun requestDailyWeatherApiService(
        lat: String,
        lon: String,
        dailyData: (ArrayList<Daily>) -> Unit
    ) {
        weatherApiService.getWeather(lat, lon, context.getString(R.string.app_id), "metric")
            ?.enqueue(object : Callback<WeatherApi> {
                override fun onFailure(call: Call<WeatherApi>, t: Throwable) {
                    println("일별 날씨 응답 실패 : $t")
                }

                override fun onResponse(call: Call<WeatherApi>, response: Response<WeatherApi>) {
                    println("일별 날씨 응답 성공 : ${response.body().toString()}")

                    response.body()?.let {
                        dailyData(it.daily!!)
                        for (i in 0..7) {
                            it.daily!![i].let{
                                DailyEntity(i+101,
                                    it.dt,
                                    it.temp.min,
                                    it.temp.max,
                                    it.humidity,
                                    it.clouds,
                                    it.weather[0].id,
                                    it.weather[0].main,
                                    it.weather[0].description,
                                    it.weather[0].icon
                                ).let { entity ->
                                    GlobalScope.launch {
                                        if (databaseManager.dailyDao().getDaily().size >= 8)
                                            databaseManager.dailyDao().updateDaily(entity)
                                        else
                                            databaseManager.dailyDao().insertDaily(entity)
                                    }
                                }
                            }
                        }
                    }
                }
            })
    }
}