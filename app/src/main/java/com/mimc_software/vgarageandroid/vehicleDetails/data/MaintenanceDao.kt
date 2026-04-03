package com.mimc_software.vgarageandroid.vehicleDetails.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MaintenanceDao {
    @Transaction
    @Query("SELECT * FROM MaintenanceEntity WHERE vehicleId = :vehicleId")
    suspend fun getMaintenancesFrom(vehicleId: String): List<MaintenanceEntity>?

    @Insert
    suspend fun addMaintenance(maintenance: MaintenanceEntity): Long

    @Transaction
    @Query("SELECT * FROM MaintenanceEntity WHERE uid = :maintenanceId")
    suspend fun getMaintenanceBy(maintenanceId: String): MaintenanceEntity?
}