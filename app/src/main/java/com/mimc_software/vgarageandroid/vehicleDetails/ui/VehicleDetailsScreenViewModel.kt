package com.mimc_software.vgarageandroid.vehicleDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addVehicle.data.toUiModel
import com.mimc_software.vgarageandroid.addVehicle.domain.AddVehicleUseCase
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import com.mimc_software.vgarageandroid.vehicleDetails.domain.GetVehicleResult
import com.mimc_software.vgarageandroid.vehicleDetails.domain.GetVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class VehicleDetailsUiState {
    object Idle : VehicleDetailsUiState()
    object Loading : VehicleDetailsUiState()
    data class Loaded(val vehicleDetails: VehicleModel) : VehicleDetailsUiState()
    object  Empty : VehicleDetailsUiState()
    data class Error(val message: String) : VehicleDetailsUiState()
}

@HiltViewModel
class VehicleDetailsScreenViewModel @Inject constructor(
    private val getVehicleByIdUseCase: GetVehicleUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<VehicleDetailsUiState>(VehicleDetailsUiState.Idle)
    val uiState: StateFlow<VehicleDetailsUiState> = _uiState

    fun onGetVehicleDetails(vehicleId: String) {
        viewModelScope.launch {
            _uiState.value = VehicleDetailsUiState.Loading

            when(val result = getVehicleByIdUseCase(vehicleId)) {
                is GetVehicleResult.Success -> {
                    _uiState.value = VehicleDetailsUiState.Loaded(result.vehicle.toUiModel())
                }
                else -> {
                    _uiState.value = VehicleDetailsUiState.Error("Error: No se pudieron obtener los detalles del vehículo.")
                }
            }
        }
    }
}