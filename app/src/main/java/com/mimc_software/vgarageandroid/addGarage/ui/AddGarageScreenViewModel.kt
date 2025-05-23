package com.mimc_software.vgarageandroid.addGarage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addGarage.domain.AddGarageUseCase
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class AddGarageScreenViewModel @Inject constructor(
    private val addGarageUseCase: AddGarageUseCase
): ViewModel() {

    @OptIn(ExperimentalUuidApi::class)
    fun onGarageCreated(garageName: String) {
        viewModelScope.launch {
            addGarageUseCase(GarageModel(
                uid = Uuid.random().toString(),
                name = garageName,
                active = true
            ))
        }
    }
}