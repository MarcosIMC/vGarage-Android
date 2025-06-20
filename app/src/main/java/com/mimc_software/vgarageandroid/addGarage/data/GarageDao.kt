package com.mimc_software.vgarageandroid.addGarage.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GarageDao {
    @Insert
    suspend fun addGarage(garage: GarageEntity)

    @Query("SELECT * from GarageEntity WHERE active == true")
    suspend fun getActiveGarage(): GarageEntity?
}