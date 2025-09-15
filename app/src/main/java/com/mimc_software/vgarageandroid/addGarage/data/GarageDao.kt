package com.mimc_software.vgarageandroid.addGarage.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GarageDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addGarage(garage: GarageEntity): Long

    @Query("SELECT * from GarageEntity WHERE active == true")
    suspend fun getActiveGarage(): GarageEntity?
}