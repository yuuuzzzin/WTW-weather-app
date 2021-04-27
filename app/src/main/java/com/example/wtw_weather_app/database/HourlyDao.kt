package com.example.wtw_weather_app.database

import androidx.room.*

@Dao
interface HourlyDao {
    @Transaction
    @Query("SELECT * FROM hourlyENTITY")
    fun getHourly(): List<HourlyEntity>

    @Insert
    fun insertHourly(vararg hourlyENTITY: HourlyEntity)

    @Delete
    fun deleteHourly(vararg hourlyENTITY: HourlyEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHourly(vararg hourlyENTITY: HourlyEntity)
}