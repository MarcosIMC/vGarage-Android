package com.mimc_software.vgarageandroid.addVehicle.domain

sealed class AddVehicleResult {
    data class Success(val vehicleId: Long) : AddVehicleResult()
    object Duplicate : AddVehicleResult()
    data class Error(val exception: Throwable) : AddVehicleResult()
}