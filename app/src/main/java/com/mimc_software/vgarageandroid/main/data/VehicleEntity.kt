package com.mimc_software.vgarageandroid.main.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mimc_software.vgarageandroid.main.ui.model.VehicleModel
import java.util.Date

@Entity
data class VehicleEntity (
    @PrimaryKey val uid: String,
    @ColumnInfo (name = "brand") val brand: String,
    @ColumnInfo (name = "model") val model: String?,
    @ColumnInfo (name = "year") val year: Date?,
    @ColumnInfo (name = "revision") val revision: Date?,
    @ColumnInfo (name = "image") val image: String?, // Image stored as a URI string
    @ColumnInfo (name = "others") val others: String?,
    @ColumnInfo (name = "garageId") val garageId: String
)

fun VehicleEntity.toUiModel(): VehicleModel {
    return VehicleModel(
        uid = this.uid,
        brand = this.brand,
        model = this.model.toString(),
        year = this.year.toString(),
        revision = this.revision.toString(),
        image = this.image.toString(),
        others = this.others.toString(),
        displayName = "${this.brand} ${this.model}"
    )
}