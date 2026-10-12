package com.mimc_software.vgarageandroid.addVehicle.data

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(private val vehicleDao: VehicleDao) {

    suspend fun add(vehicleEntity: VehicleEntity): Long {
        Log.d("VehicleRepository", "Adding vehicle: uid=${vehicleEntity.uid}, brand=${vehicleEntity.brand}, garageId=${vehicleEntity.garageId}")
        val result = vehicleDao.addVehicle(vehicleEntity)
        Log.d("VehicleRepository", "Vehicle added with ID: $result")
        return result
    }

    suspend fun getVehiclesBy(garageId: String): List<VehicleEntity>? {
        Log.d("VehicleRepository", "Getting vehicles for garageId: $garageId")
        val vehicles = vehicleDao.getVehiclesFrom(garageId)
        Log.d("VehicleRepository", "Found ${vehicles?.size ?: 0} vehicles")
        return vehicles
    }

    suspend fun getVehicleBy(vehicleId: String): VehicleEntity? {
        Log.d("VehicleRepository", "Getting vehicle by ID: $vehicleId")
        val vehicle = vehicleDao.getVehicleBy(vehicleId)
        if (vehicle != null) {
            Log.d("VehicleRepository", "Vehicle found: uid=${vehicle.uid}, brand=${vehicle.brand}, garageId=${vehicle.garageId}")
        } else {
            Log.d("VehicleRepository", "No vehicle found with ID: $vehicleId")
        }
        return vehicle
    }
}