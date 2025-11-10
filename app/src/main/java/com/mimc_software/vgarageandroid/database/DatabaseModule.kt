package com.mimc_software.vgarageandroid.database

import android.content.Context
import androidx.room.Room
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideGarageDao(appDatabase: AppDatabase): GarageDao {
        return appDatabase.garageDao()
    }

    @Provides
    fun provideVehicleDao(appDatabase: AppDatabase): VehicleDao {
        return appDatabase.vehicleDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "vGarage").build()
    }
}