package com.mimc_software.vgarageandroid.main.domain

import com.mimc_software.vgarageandroid.main.data.VehicleEntity
import com.mimc_software.vgarageandroid.main.data.VehicleRepository
import javax.inject.Inject

class GetVehiclesByGarageUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
){
    suspend operator fun invoke(garageId: String): List<VehicleEntity> {
        return vehicleRepository.getVehiclesBy(garageId)
    }
}