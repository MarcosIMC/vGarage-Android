package com.mimc_software.vgarageandroid.addVehicle.domain

import android.database.sqlite.SQLiteConstraintException
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleRepository
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import com.mimc_software.vgarageandroid.addVehicle.ui.model.toEntity
import javax.inject.Inject

class AddVehicleUseCase @Inject constructor(
    private val vehicleRepository: VehicleRepository
) {
    suspend operator fun invoke(vehicleModel: VehicleModel): AddVehicleResult {
        return try {
            val newVehicleId = vehicleRepository.add(vehicleModel.toEntity())
            AddVehicleResult.Success(newVehicleId)
        } catch (e: SQLiteConstraintException) {
            AddVehicleResult.Duplicate
        } catch (e : Exception) {
            AddVehicleResult.Error(e)
        }
    }
}