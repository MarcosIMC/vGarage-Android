package com.mimc_software.vgarageandroid.main.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class VehicleEntity (
    @PrimaryKey val uid: String,
    @ColumnInfo (name = "brand") val brand: String,
    @ColumnInfo (name = "model") val model: String,
    @ColumnInfo (name = "year") val year: Date,
    @ColumnInfo (name = "revision") val revision: Date,
    @ColumnInfo (name = "image") val image: ImageBitmap,
    @ColumnInfo (name = "others") val others: String,
    @ColumnInfo (name = "garageId") val garageId: String
)