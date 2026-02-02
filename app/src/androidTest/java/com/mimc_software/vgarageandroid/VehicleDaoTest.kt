package com.mimc_software.vgarageandroid

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addGarage.data.GarageRepository
import com.mimc_software.vgarageandroid.addGarage.domain.AddGarageUseCase
import com.mimc_software.vgarageandroid.database.AppDatabase
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleDao
import com.mimc_software.vgarageandroid.addVehicle.data.VehicleRepository
import com.mimc_software.vgarageandroid.addVehicle.domain.AddVehicleResult
import com.mimc_software.vgarageandroid.addVehicle.domain.AddVehicleUseCase
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class VehicleDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var vehicleDao: VehicleDao
    private lateinit var garageDao: GarageDao
    private lateinit var  addVehicleUseCase: AddVehicleUseCase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        vehicleDao = db.vehicleDao()
        addVehicleUseCase = AddVehicleUseCase(
            vehicleRepository = VehicleRepository(
                vehicleDao = vehicleDao
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun insertNewVehicle() = runBlocking {
        val result = addVehicleUseCase(
            VehicleModel(
                uid = Uuid.Companion.random().toString(),
                brand = "Toyota",
                model = "Corolla",
                year = "2020-01-01",
                revision = "2021-01-01",
                image = "content://images/vehicle_1",
                others = "Some other details",
                displayName = "Toyota Corolla"
            )
        )
        assert(result is AddVehicleResult.Success)
    }
}