package com.mimc_software.vgarageandroid.addVehicle

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject

data class AddVehicleUiState (
    val vehicleName: String = "",
    val vehicleBrand: String = "",
    val vehicleYear: Date = Date(),
    val vehicleRevision: Date = Date(),
    val vehicleImage: String = "",
    val vehicleOthers: String = "",
    val garageId: String = "",
)

@HiltViewModel
class AddVehicleViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(AddVehicleUiState())
    val uiState: StateFlow<AddVehicleUiState> = _uiState

    fun _onVehicleNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleName = newValue)
    }
    fun _onVehicleBrandChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleBrand = newValue)
    }
    fun _onVehicleYearChange(newValue: Date) {
        _uiState.value = _uiState.value.copy(vehicleYear = newValue)
    }
    fun _onVehicleRevisionChange(newValue: Date) {
        _uiState.value = _uiState.value.copy(vehicleRevision = newValue)
    }
    fun _onVehicleImageChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleImage = newValue)
    }
    fun _onVehicleOthersChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleOthers = newValue)
    }
    fun _onGarageIdChange(newValue: String) {
        _uiState.value = _uiState.value.copy(garageId = newValue)
    }
}