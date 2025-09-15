package com.mimc_software.vgarageandroid.addGarage.domain

import android.database.sqlite.SQLiteConstraintException
import com.mimc_software.vgarageandroid.addGarage.data.GarageRepository
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import javax.inject.Inject

class AddGarageUseCase @Inject constructor(
    private val garageRepository: GarageRepository
) {
    suspend operator fun invoke(garageModel: GarageModel): AddGarageResult {
        return try {
            val newGarageId = garageRepository.add(garageModel)
            AddGarageResult.Success(newGarageId)
        } catch (e: SQLiteConstraintException) {
            AddGarageResult.Duplicate
        } catch (e: Exception) {
            AddGarageResult.Error(e)
        }
    }
}