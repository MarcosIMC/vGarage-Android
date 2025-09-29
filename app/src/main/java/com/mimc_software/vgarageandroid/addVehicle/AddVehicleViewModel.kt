package com.mimc_software.vgarageandroid.addVehicle

import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.LocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddVehicleUiState(
    val vehicleName: String = "",
    val vehicleBrand: String = "",
    val vehicleYear: Long? = null,
    val vehicleRevision: Long? = null,
    val vehicleImage: String? = null,
    val vehicleOthers: String = "",
    val garageId: String = "",
)

sealed class AddVehicleUiEvent {
    object OpenCamera: AddVehicleUiEvent()
}

@HiltViewModel
class AddVehicleViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(AddVehicleUiState())
    val uiState: StateFlow<AddVehicleUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<AddVehicleUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val vehicleNameHasError: Boolean
        get() = _uiState.value.vehicleName.isEmpty() || "[0-9]".toRegex()
            .containsMatchIn(_uiState.value.vehicleName)

    fun _onVehicleNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleName = newValue)
    }

    fun _onVehicleBrandChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleBrand = newValue)
    }

    fun _onVehicleYearChange(newValue: Long?) {
        print("Hora: $newValue ")
        _uiState.value = _uiState.value.copy(vehicleYear = newValue)
    }

    fun _onVehicleRevisionChange(newValue: Long?) {
        _uiState.value = _uiState.value.copy(vehicleRevision = newValue)
    }

    fun _onVehicleImageChange(newValue: String?) {
        _uiState.value = _uiState.value.copy(vehicleImage = newValue)
    }

    fun _onVehicleOthersChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleOthers = newValue)
    }

    fun _onGarageIdChange(newValue: String) {
        _uiState.value = _uiState.value.copy(garageId = newValue)
    }

    fun onCameraOpen() {
        viewModelScope.launch {
            _uiEvent.emit(AddVehicleUiEvent.OpenCamera)
        }
    }

    fun onPhotoCaptured(uri: Uri) {
        _onVehicleImageChange(uri.toString())
    }
}