package com.mimc_software.vgarageandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addGarage.data.GarageEntity

@Database(
    entities = [GarageEntity::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun garageDao(): GarageDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vGarage.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}