package com.mimc_software.vgarageandroid.addVehicle.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface VehicleDao {
    @Transaction
    @Query("SELECT * FROM VehicleEntity WHERE garageId = :garageId")
    suspend fun getVehiclesFrom(garageId: String): List<VehicleEntity>?

    @Insert
    suspend fun addVehicle(vehicle: VehicleEntity): Long

    @Transaction
    @Query("SELECT * FROM VehicleEntity WHERE uid = :vehicleId")
    suspend fun getVehicleBy(vehicleId: String): VehicleEntity?
}