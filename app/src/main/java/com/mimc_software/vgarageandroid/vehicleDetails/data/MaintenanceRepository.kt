package com.mimc_software.vgarageandroid.vehicleDetails.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaintenanceRepository @Inject constructor(private val maintenanceDao: MaintenanceDao) {

    suspend fun add(maintenanceEntity: MaintenanceEntity): Long {
        return maintenanceDao.addMaintenance(maintenanceEntity)
    }

    suspend fun getMaintenancesBy(vehicleId: String): List<MaintenanceEntity>? {
        return maintenanceDao.getMaintenancesFrom(vehicleId)
    }

     suspend fun getMaintenanceBy(maintenanceId: String): MaintenanceEntity? {
        return maintenanceDao.getMaintenanceBy(maintenanceId)
    }
}