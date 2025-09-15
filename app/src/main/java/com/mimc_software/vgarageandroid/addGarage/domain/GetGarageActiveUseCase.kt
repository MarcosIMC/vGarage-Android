package com.mimc_software.vgarageandroid.addGarage.domain

import com.mimc_software.vgarageandroid.addGarage.data.GarageRepository
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import javax.inject.Inject

class GetGarageActiveUseCase @Inject constructor(
    private val garageRepository: GarageRepository
) {
    suspend operator fun invoke(): GarageModel? {
        return garageRepository.getActiveGarage()
    }
}