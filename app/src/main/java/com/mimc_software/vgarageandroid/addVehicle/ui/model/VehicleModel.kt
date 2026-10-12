package com.mimc_software.vgarageandroid.addVehicle.ui.model

import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity
import java.time.LocalDate

data class VehicleModel (
    val uid: String,
    val brand: String,
    val model: String,
    val year: LocalDate?,
    val revision: LocalDate?,
    val image: String,
    val others: String,
    val displayName: String,
    val garageId: String = ""
)

fun VehicleModel.toEntity() = VehicleEntity(
    uid = this.uid,
    brand = this.brand,
    model = this.model,
    year = this.year,
    revision = this.revision,
    image = this.image,
    others = this.others,
    garageId = this.garageId
)