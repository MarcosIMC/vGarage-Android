package com.mimc_software.vgarageandroid.vehicleDetails.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.MaintenanceModel
import java.util.Date

@Entity
data class MaintenanceEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo (name = "title") val title: String,
    @ColumnInfo (name = "date") val date: Date?,
    @ColumnInfo (name = "price") val price: Double,
    @ColumnInfo (name = "description") val description: String,
    @ColumnInfo (name = "kindMaintenance") val kindMaintenance: String,
    @ColumnInfo (name = "vehicleId") val vehicleId: String
)

fun MaintenanceEntity.toUiModel() = MaintenanceModel(
    uid = this.uid,
    title = this.title,
    date = this.date.toString(),
    price = this.price,
    description = this.description,
    kindMaintenance = this.kindMaintenance,
    vehicleId = this.vehicleId
)