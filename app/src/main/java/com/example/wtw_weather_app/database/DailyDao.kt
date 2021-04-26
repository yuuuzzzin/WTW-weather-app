package com.example.wtw_weather_app.database

import androidx.room.*

@Dao
interface DailyDao {
    @Transaction
    @Query("SELECT * FROM dailyENTITY")
    fun getCurrent(): List<DailyEntity>

    @Insert
    fun insertCurrent(vararg dailyENTITY: DailyEntity)

    @Delete
    fun deleteCurrent(vararg dailyENTITY: DailyEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCurrent(vararg dailyENTITY: DailyEntity)
}