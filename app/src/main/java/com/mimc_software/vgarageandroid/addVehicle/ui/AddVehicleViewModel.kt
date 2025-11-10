package com.mimc_software.vgarageandroid.addVehicle.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addVehicle.domain.AddVehicleResult
import com.mimc_software.vgarageandroid.addVehicle.domain.AddVehicleUseCase
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AddVehicleUiState {
    object Idle: AddVehicleUiState()
    object Loading: AddVehicleUiState()
    object Success: AddVehicleUiState()
    data class Error(val message: String): AddVehicleUiState()
}

data class AddVehicleDataUiState(
    val vehicleName: String = "",
    val vehicleBrand: String = "",
    val vehicleYear: Long? = null,
    val vehicleRevision: Long? = null,
    val vehicleImage: String? = null,
    val vehicleOthers: String = "",
    val garageId: String = "",
)

sealed class AddVehicleUiCameraEvent {
    object OpenCamera: AddVehicleUiCameraEvent()
}

sealed class AddVehicleUiEvent {
    data class ShowSnackbar(val message: String): AddVehicleUiEvent()
    object NavigationToMain: AddVehicleUiEvent()
}

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    private val addVehicleUseCase: AddVehicleUseCase
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow<AddVehicleUiState>(AddVehicleUiState.Idle)
    val uiStateFlow: StateFlow<AddVehicleUiState> = _uiStateFlow
    private val _uiState = MutableStateFlow(AddVehicleDataUiState())
    val uiState: StateFlow<AddVehicleDataUiState> = _uiState

    private val _uiCameraEvent = MutableSharedFlow<AddVehicleUiCameraEvent>()
    val uiCameraEvent = _uiCameraEvent.asSharedFlow()

    private val _uiEvent = Channel<AddVehicleUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val vehicleNameHasError: Boolean
        get() = _uiState.value.vehicleName.isEmpty() || "[0-9]".toRegex()
            .containsMatchIn(_uiState.value.vehicleName)

    val vehicleModelHasError: Boolean
        get() = _uiState.value.vehicleBrand.isEmpty()

    fun _onVehicleNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleName = newValue)
    }

    fun _onVehicleBrandChange(newValue: String) {
        _uiState.value = _uiState.value.copy(vehicleBrand = newValue)
    }

    fun _onVehicleYearChange(newValue: Long?) {
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

    fun validateForm(): Boolean {
        return !vehicleNameHasError && !vehicleModelHasError
    }

    fun onCameraOpen() {
        viewModelScope.launch {
            _uiCameraEvent.emit(AddVehicleUiCameraEvent.OpenCamera)
        }
    }

    fun onPhotoCaptured(uri: Uri) {
        _onVehicleImageChange(uri.toString())
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun onAddVehicle(newVehicle: VehicleModel) {
        viewModelScope.launch {
            _uiStateFlow.value = AddVehicleUiState.Loading

            when (val result = addVehicleUseCase(newVehicle)) {
                is AddVehicleResult.Success -> {
                    _uiState.value = AddVehicleDataUiState()
                    _uiStateFlow.value = AddVehicleUiState.Idle
                    _uiEvent.send(AddVehicleUiEvent.ShowSnackbar("Vehículo agregado correctamente."))
                    _uiEvent.send(AddVehicleUiEvent.NavigationToMain)
                }
                is AddVehicleResult.Duplicate -> {
                    _uiStateFlow.value = AddVehicleUiState.Error("El vehículo ya existe.")
                }
                is AddVehicleResult.Error -> {
                    _uiStateFlow.value = AddVehicleUiState.Error("Error: ${result.exception}")
                }
            }
        }
    }
}