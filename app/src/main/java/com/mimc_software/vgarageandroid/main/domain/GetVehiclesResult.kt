package com.mimc_software.vgarageandroid.main.domain

import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity

sealed class GetVehiclesResult {
    data class Success(val vehicles: List<VehicleEntity>?): GetVehiclesResult()
    data class Error(val exception: Throwable): GetVehiclesResult()
}