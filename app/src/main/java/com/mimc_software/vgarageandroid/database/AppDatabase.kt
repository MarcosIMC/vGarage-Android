package com.mimc_software.vgarageandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addGarage.data.GarageEntity

@Database(
    entities = [GarageEntity::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun garageDao(): GarageDao
}