package com.mimc_software.vgarageandroid.vehicleDetails.domain

import com.mimc_software.vgarageandroid.vehicleDetails.data.MaintenanceEntity

sealed class GetMaintenancesResult {
    data class Success(val maintenances: List<MaintenanceEntity>?): GetMaintenancesResult()
    data class Error(val exception: Throwable): GetMaintenancesResult()
}