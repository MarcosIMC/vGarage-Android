package com.mimc_software.vgarageandroid.vehicleDetails.domain

import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleRepository
import javax.inject.Inject

class GetVehicleUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(vehicleId: String): GetVehicleResult {
        return try {
            val vehicle = vehicleRepository.getVehicleBy(vehicleId)
            if (vehicle != null) {
                GetVehicleResult.Success(vehicle)
            } else {
                GetVehicleResult.Empty
            }
        } catch (e: Exception) {
            GetVehicleResult.Error(e)
        }
    }
}