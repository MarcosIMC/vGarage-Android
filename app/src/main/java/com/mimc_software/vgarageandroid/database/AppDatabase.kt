package com.mimc_software.vgarageandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addGarage.data.GarageEntity
import com.mimc_software.vgarageandroid.main.data.VehicleDao
import com.mimc_software.vgarageandroid.main.data.VehicleEntity

@Database(
    entities = [GarageEntity::class, VehicleEntity::class],
    version = 2
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun garageDao(): GarageDao
    abstract fun vehicleDao(): VehicleDao
}