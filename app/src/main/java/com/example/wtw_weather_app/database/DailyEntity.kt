package com.example.wtw_weather_app.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailyENTITY")
data class DailyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var dt: Long = 0L,
    @ColumnInfo var min_temp: Double = 0.0,
    @ColumnInfo var max_temp: Double = 0.0,
    @ColumnInfo var feels_like: Double = 0.0,
    @ColumnInfo var humidity: Int = 0,
    @ColumnInfo var clouds: Int = 0,
    @ColumnInfo var rain: Int = 0,
    @ColumnInfo var snow: Int = 0,
    @ColumnInfo var weather_id: Int = 0,
    @ColumnInfo var main: String = "",
    @ColumnInfo var description: String = "",
    @ColumnInfo var icon: String =""
)
