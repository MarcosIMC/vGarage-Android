package com.mimc_software.vgarageandroid.vehicleDetails.domain

import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity

sealed class GetVehicleResult {
    data class Success(val vehicle: VehicleEntity): GetVehicleResult()
    object Empty: GetVehicleResult()
    data class Error(val exception: Throwable): GetVehicleResult()
}