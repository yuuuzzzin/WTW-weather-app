package com.example.wtw_weather_app.database

import androidx.room.*

@Dao
interface HourlyDao {
    @Transaction
    @Query("SELECT * FROM hourlyENTITY")
    fun getCurrent(): List<HourlyEntity>

    @Insert
    fun insertCurrent(vararg hourlyENTITY: HourlyEntity)

    @Delete
    fun deleteCurrent(vararg hourlyENTITY: HourlyEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCurrent(vararg hourlyENTITY: HourlyEntity)
}