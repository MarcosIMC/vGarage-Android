package com.mimc_software.vgarageandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addGarage.data.GarageEntity
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleDao
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleEntity
import com.mimc_software.vgarageandroid.vehicleDetails.data.MaintenanceDao
import com.mimc_software.vgarageandroid.vehicleDetails.data.MaintenanceEntity

@Database(
    entities = [GarageEntity::class, VehicleEntity::class, MaintenanceEntity::class],
    version = 3
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun garageDao(): GarageDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun maintenanceDao(): MaintenanceDao
}