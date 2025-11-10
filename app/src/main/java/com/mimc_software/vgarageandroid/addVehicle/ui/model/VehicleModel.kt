package com.mimc_software.vgarageandroid.addVehicle.ui.model

import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity

data class VehicleModel (
    val uid: String,
    val brand: String,
    val model: String,
    val year: String,
    val revision: String,
    val image: String,
    val others: String,
    val displayName: String
)

fun VehicleModel.toEntity() = VehicleEntity(
    uid = this.uid,
    brand = this.brand,
    model = this.model,
    year = null,
    revision = null,
    image = this.image,
    others = this.others,
    garageId = ""
)