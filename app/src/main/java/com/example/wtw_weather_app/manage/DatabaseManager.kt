package com.example.wtw_weather_app.manage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wtw_weather_app.database.*

@Database(entities = arrayOf(CurrentEntity::class, HourlyEntity::class, DailyEntity::class), version = 1)
abstract class DatabaseManager: RoomDatabase() {
    abstract fun currentDao(): CurrentDao
    abstract fun hourlyDao(): HourlyDao
    abstract fun dailyDao(): DailyDao

    companion object {
        private var instance: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            instance?.let { return instance!! } ?: synchronized(DatabaseManager::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseManager::class.java,
                    "weather.db"
                ).build()
                return instance!!
            }
        }
    }
}