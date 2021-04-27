package com.example.wtw_weather_app.database

import androidx.room.*

@Dao
interface DailyDao {
    @Transaction
    @Query("SELECT * FROM dailyENTITY")
    fun getDaily(): List<DailyEntity>

    @Insert
    fun insertDaily(vararg dailyENTITY: DailyEntity)

    @Delete
    fun deleteDaily(vararg dailyENTITY: DailyEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDaily(vararg dailyENTITY: DailyEntity)
}