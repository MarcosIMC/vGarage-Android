package com.mimc_software.vgarageandroid.vehicleDetails.ui.model

import com.mimc_software.vgarageandroid.vehicleDetails.data.MaintenanceEntity

data class MaintenanceModel(
    val uid: String,
    val title: String,
    val date: String,
    val price: Double,
    val description: String,
    val kindMaintenance: String,
    val vehicleId: String
)

fun MaintenanceModel.toEntity() = MaintenanceEntity(
    uid = this.uid,
    title = this.title,
    date = null,
    price = this.price,
    description = this.description,
    kindMaintenance = this.kindMaintenance,
    vehicleId = this.vehicleId
)