package com.mimc_software.vgarageandroid.addGarage.domain

import javax.inject.Inject

class AddGarageUseCase @Inject constructor(
    private val garageRepository: GarageRepository
) {
    suspend operator fun invoke(garageModel)
}