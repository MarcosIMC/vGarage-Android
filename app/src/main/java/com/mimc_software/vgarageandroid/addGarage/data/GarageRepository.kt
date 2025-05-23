package com.mimc_software.vgarageandroid.addGarage.data

import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import javax.inject.Inject

class GarageRepository @Inject constructor(private val garageDao: GarageDao) {
    suspend fun add(garageModel: GarageModel) {
        garageDao.addGarage
    }
}