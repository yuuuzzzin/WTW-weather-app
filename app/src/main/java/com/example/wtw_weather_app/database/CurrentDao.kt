package com.example.wtw_weather_app.database

import androidx.room.*

@Dao
interface CurrentDao {
    @Transaction
    @Query("SELECT * FROM currentENTITY")
    fun getCurrent(): List<CurrentEntity>

    @Insert
    fun insertCurrent(vararg currentENTITY: CurrentEntity)

    @Delete
    fun deleteCurrent(vararg currentENTITY: CurrentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCurrent(vararg currentENTITY: CurrentEntity)
}