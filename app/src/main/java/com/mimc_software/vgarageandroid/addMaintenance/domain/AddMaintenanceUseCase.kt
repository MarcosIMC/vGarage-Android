package com.mimc_software.vgarageandroid.addMaintenance.domain

import android.database.sqlite.SQLiteConstraintException
import com.mimc_software.vgarageandroid.vehicleDetails.data.MaintenanceRepository
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.MaintenanceModel
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.toEntity
import javax.inject.Inject

class AddMaintenanceUseCase @Inject constructor(
    private val maintenanceRepository: MaintenanceRepository
) {
    suspend operator fun invoke(maintenanceModel: MaintenanceModel): AddMaintenanceResult {
        return try {
            val newMaintenanceId = maintenanceRepository.add(maintenanceModel.toEntity())
            AddMaintenanceResult.Success(newMaintenanceId)
        } catch (e: SQLiteConstraintException) {
            AddMaintenanceResult.Duplicate
        } catch (e: Exception) {
            AddMaintenanceResult.Error(e)
        }
    }
}