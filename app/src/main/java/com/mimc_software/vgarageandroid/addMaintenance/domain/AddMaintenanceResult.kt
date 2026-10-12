package com.mimc_software.vgarageandroid.addMaintenance.domain


sealed class AddMaintenanceResult {
    data class Success(val maintenanceId: Long) : AddMaintenanceResult()
    object Duplicate : AddMaintenanceResult()
    data class Error(val exception: Throwable) : AddMaintenanceResult()
}