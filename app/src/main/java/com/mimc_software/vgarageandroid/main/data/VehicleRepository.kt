package com.mimc_software.vgarageandroid.main.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(private val vehicleDao: VehicleDao) {

    suspend fun add(vehicleEntity: VehicleEntity): Long {
        return vehicleDao.addVehicle(vehicleEntity)
    }

    suspend fun getVehiclesBy(garageId: String): List<VehicleEntity>? {
        return vehicleDao.getVehiclesFrom(garageId)
    }
}