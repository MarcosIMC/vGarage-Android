package com.mimc_software.vgarageandroid

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mimc_software.vgarageandroid.addGarage.data.GarageDao
import com.mimc_software.vgarageandroid.addGarage.data.GarageRepository
import com.mimc_software.vgarageandroid.addGarage.domain.AddGarageUseCase
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import com.mimc_software.vgarageandroid.database.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class GarageDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var garageDao: GarageDao
    private lateinit var addGarageUseCase: AddGarageUseCase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        garageDao = db.garageDao()
        addGarageUseCase = AddGarageUseCase(
            garageRepository = GarageRepository(
                garageDao = garageDao
            )
        )
    }

    @After
    fun teardown() {
        db.close()
    }

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun insertNewGarage() = runBlocking {
        addGarageUseCase(
            GarageModel(
                uid = Uuid.Companion.random().toString(),
                name = "prueba",
                active = true
            )
        )
        val result = garageDao.getActiveGarage()
        assert(result?.name == "prueba")
    }
}