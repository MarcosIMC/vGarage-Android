package com.mimc_software.vgarageandroid.main.domain

import com.mimc_software.vgarageandroid.main.data.VehicleRepository
import javax.inject.Inject

class GetVehiclesByGarageUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
){
    suspend operator fun invoke(garageId: String): GetVehiclesResult {
        return try {
            val vehicles = vehicleRepository.getVehiclesBy(garageId)
            GetVehiclesResult.Success(vehicles)
        } catch (e: Exception) {
            GetVehiclesResult.Error(e)
        }
    }
}