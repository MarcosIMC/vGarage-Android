package com.mimc_software.vgarageandroid.main.data

import androidx.room.Embedded
import androidx.room.Relation
import com.mimc_software.vgarageandroid.addGarage.data.GarageEntity
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity

data class VehiclesFromGarage(
    @Embedded val garage: GarageEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "garageId"
    )
    val vehicles: List<VehicleEntity>
)
