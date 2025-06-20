package com.mimc_software.vgarageandroid.addGarage.data

import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GarageRepository @Inject constructor(private val garageDao: GarageDao) {

    suspend fun add(garageModel: GarageModel) {
        garageDao.addGarage(GarageEntity(garageModel.uid, garageModel.name, garageModel.active))
    }

    suspend fun getActiveGarage(): GarageModel? {
        val garageEntity = garageDao.getActiveGarage()

        if (garageEntity != null) {
            return GarageModel(
                uid = garageEntity.uid,
                name = garageEntity.name,
                active = garageEntity.active
            )
        }
        return null
    }
}