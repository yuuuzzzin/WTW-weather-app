package com.example.wtw_weather_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.wtw_weather_app.adapter.DailyAdapter
import com.example.wtw_weather_app.adapter.HourlyAdapter
import com.example.wtw_weather_app.api_model.CurrentAndHourly
import com.example.wtw_weather_app.api_model.Daily
import com.example.wtw_weather_app.api_model.DailyTemp
import com.example.wtw_weather_app.api_model.Weather
import com.example.wtw_weather_app.database.CurrentEntity
import com.example.wtw_weather_app.database.DailyEntity
import com.example.wtw_weather_app.database.HourlyEntity
import com.example.wtw_weather_app.manage.DatabaseManager
import com.example.wtw_weather_app.manage.NetworkHelper
import com.example.wtw_weather_app.set.DailySet
import com.example.wtw_weather_app.set.HourlySet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_daily.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var locationManager: com.example.wtw_weather_app.manage.LocationManager
    lateinit var networkHelper: NetworkHelper
    lateinit var databaseManager: DatabaseManager

    var location: Location? = null
    var currentLocation: String = ""
    val LOCATION_REQUEST_CODE = 200

    var hourlyAdapter = HourlyAdapter()
    var dailyAdapter = DailyAdapter()

    lateinit var setHourlyData : (ArrayList<CurrentAndHourly>) -> Unit
    lateinit var setCurrentData : (CurrentAndHourly) -> Unit
    lateinit var setDailyData : (ArrayList<Daily>) -> Unit

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            location = locationManager.locationPermissionResult()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewFunctionOfCurrentData()
        initViewFunctionOfHourlyData()
        initViewFunctionOfDailyData()

        locationManager = com.example.wtw_weather_app.manage.LocationManager(this)
        networkHelper = NetworkHelper(this)

        hourlyWeatherRecycler.apply {
            adapter = hourlyAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(applicationContext, 0))
        }

        dailyWeatherRecycler.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        getDatabaseAndSetView()

        refresh_button.setOnClickListener {
            getWeatherApi()
        }
    }

    private fun getDatabaseAndSetView() {
        GlobalScope.launch {
            databaseManager = DatabaseManager.getInstance(applicationContext)

            println("?????????????????? ??????")
            getWeatherApi()
        }
    }

    private fun getWeatherApi() {
        GlobalScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                location = locationManager.requestLocationPermissions()
                println("??????!!!!!!!"+ location?.latitude.toString()+ location?.longitude.toString())
            }

            var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)
            var mResultList: List<Address>? = null
            try {
                mResultList = mGeocoder.getFromLocation(
                    location?.latitude!!, location?.longitude!!, 1
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (mResultList != null) {
                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
                currentLocation = mResultList.get(0).getAdminArea() + " " + mResultList.get(0).getThoroughfare()
                println("????????? ?????? ?????? : " + currentLocation)
                districtText.text = "${currentLocation}"
            }

            location?.let {
                launch(Dispatchers.IO) { // current
                    networkHelper.requestCurrentWeatherApiService(it.latitude.toString(), it.longitude.toString(), setCurrentData)
                }
                launch(Dispatchers.IO) { // hourly
                    networkHelper.requestHourlyWeatherApiService(it.latitude.toString(), it.longitude.toString(), setHourlyData)
                }
                launch(Dispatchers.IO) { // daily
                    networkHelper.requestDailyWeatherApiService(it.latitude.toString(), it.longitude.toString(), setDailyData)
                }
            } ?: launch(Dispatchers.Main) {
                Toast.makeText(applicationContext, "There is no location information.", Toast.LENGTH_SHORT).show()
            }

            //refresh_button.isClickable = true
        }
    }

    private fun initViewFunctionOfCurrentData() {

        setCurrentData = { model ->
            println("current ??????")
            model.weather[0].id

            Glide.with(this)
                .load("https://openweathermap.org/img/wn/${model.weather[0].icon}@2x.png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
                .into(currentWeatherImage)
            currentTempText.text = "${model.temp.toInt()}${"???"}"
        }
    }

    // @SuppressLint("NewApi")??? ?????? ??????????????? ?????? ??? minSdkVersion ????????? ?????? API??? ????????????  warning??? ????????? ???????????? ?????? APi??? ????????? ??? ?????? ?????????.
    @SuppressLint("SimpleDateFormat")
    fun initViewFunctionOfHourlyData() {
        setHourlyData = { model ->
            println("hourly ??????")
            while(hourlyAdapter.itemCount>0) hourlyAdapter.removeItem(0)
            // ????????? ?????? recycler view ??????
            for (i in 0..23) {
                model.get(i)?.let { item ->
                    hourlyAdapter.addItem(
                        HourlySet(
                            SimpleDateFormat("HH???").format(item.dt!! * 1000L),
                            "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png",
                            "${(item.temp).toInt()}${"???"}"
                        )
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun initViewFunctionOfDailyData() {
        setDailyData = { model ->
            println("daily ??????")

            model.get(0)?.let { item ->
                maxandminTempText.text = "${(item.temp.min).toInt()}${"???"} / ${(item.temp.max).toInt()}${"???"}"
            }
            while(dailyAdapter.itemCount>0) dailyAdapter.removeItem(0)
            for (i in 1..7) {
                model.get(i)?.let { item ->
                    dailyAdapter.addItem(
                        DailySet(
                            SimpleDateFormat("MM/dd").format(item.dt!! * 1000L),
                            "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png",
                            "${(item.temp.max).toInt()}",
                            "${(item.temp.min).toInt()}"
                        )
                    )
                }
            }
        }
    }

    private fun setViewFromDatabase(
        current: CurrentEntity,
        hourly: List<HourlyEntity>,
        daily: List<DailyEntity>
    ) {
        current.also {
            setCurrentData(
                CurrentAndHourly(it.dt, it.temp, it.feels_like, it.humidity, it.clouds, arrayListOf(
                    Weather(it.weather_id, it.main, it.description, it.icon)
                ))
            )
        }

        var hourlyList = ArrayList<CurrentAndHourly>()
        hourly.map {
            hourlyList.add(
                CurrentAndHourly(it.dt, it.temp, it.feels_like, it.humidity, it.clouds, arrayListOf(
                    Weather(it.weather_id, it.main, it.description, it.icon)))
            )
        }
        setHourlyData(hourlyList)

        var dailyList = ArrayList<Daily>()
        daily.map {
            dailyList.add(
                Daily(it.dt, DailyTemp(it.min_temp, it.max_temp), it.humidity, it.clouds, arrayListOf(
                    Weather(it.weather_id, it.main, it.description, it.icon)))
            )
        }
        setDailyData(dailyList)
    }
}
