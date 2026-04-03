package com.mimc_software.vgarageandroid.vehicleDetails.domain

import com.mimc_software.vgarageandroid.vehicleDetails.data.MaintenanceRepository
import javax.inject.Inject

class GetMaintenancesUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository
) {
    suspend operator fun invoke(vehicleId: String): GetMaintenancesResult {
        return try {
            val maintenances = maintenanceRepository.getMaintenancesBy(vehicleId)
            if (maintenances != null && maintenances.isNotEmpty()) {
                GetMaintenancesResult.Success(maintenances)
            } else {
                GetMaintenancesResult.Empty
            }
        } catch (e: Exception) {
            GetMaintenancesResult.Error(e)
        }
    }
}