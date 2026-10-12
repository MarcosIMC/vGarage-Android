package com.mimc_software.vgarageandroid.vehicleDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addVehicle.data.toUiModel
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import com.mimc_software.vgarageandroid.vehicleDetails.data.toUiModel
import com.mimc_software.vgarageandroid.vehicleDetails.domain.GetMaintenancesResult
import com.mimc_software.vgarageandroid.vehicleDetails.domain.GetMaintenancesUseCase
import com.mimc_software.vgarageandroid.vehicleDetails.domain.GetVehicleResult
import com.mimc_software.vgarageandroid.vehicleDetails.domain.GetVehicleUseCase
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.MaintenanceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class VehicleDetailsUiState {
    object Idle : VehicleDetailsUiState()
    object Loading : VehicleDetailsUiState()
    data class Loaded(
        val vehicleDetails: VehicleModel,
        val vehicleMaintenanceState: VehicleMaintenanceUiState,
        val vehicleParkingState: VehicleParkingState
    ) : VehicleDetailsUiState()
    object  Empty : VehicleDetailsUiState()
    data class Error(val message: String) : VehicleDetailsUiState()
}

sealed class VehicleMaintenanceUiState {
    object Idle : VehicleMaintenanceUiState()
    object Loading : VehicleMaintenanceUiState()
    data class Loaded(val maintenances: List<MaintenanceModel>) : VehicleMaintenanceUiState()
    object Empty : VehicleMaintenanceUiState()
}

sealed class VehicleParkingState {
    object Idle : VehicleParkingState()
    object Loading : VehicleParkingState()
    object Loaded : VehicleParkingState()
    object Empty : VehicleParkingState()
}

@HiltViewModel
class VehicleDetailsScreenViewModel @Inject constructor(
    private val getVehicleByIdUseCase: GetVehicleUseCase,
    private val getMaintenancesUseCase: GetMaintenancesUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<VehicleDetailsUiState>(VehicleDetailsUiState.Idle)
    val uiState: StateFlow<VehicleDetailsUiState> = _uiState
    private val _uiStateMaintenance = MutableStateFlow<VehicleMaintenanceUiState>(VehicleMaintenanceUiState.Idle)
    val uiStateMaintenance: StateFlow<VehicleMaintenanceUiState> = _uiStateMaintenance

    fun onGetVehicleDetails(vehicleId: String) {
        viewModelScope.launch {
            _uiState.value = VehicleDetailsUiState.Loading

            when(val result = getVehicleByIdUseCase(vehicleId)) {
                is GetVehicleResult.Success -> {
                    _uiState.value = VehicleDetailsUiState.Loaded(
                        result.vehicle.toUiModel(),
                        vehicleMaintenanceState = uiState.value.let {
                            when (it) {
                                is VehicleDetailsUiState.Loaded -> it.vehicleMaintenanceState
                                else -> VehicleMaintenanceUiState.Idle
                            }
                        },
                        vehicleParkingState = uiState.value.let {
                            when (it) {
                                is VehicleDetailsUiState.Loaded -> it.vehicleParkingState
                                else -> VehicleParkingState.Idle
                            }
                        }
                    )
                }
                else -> {
                    _uiState.value = VehicleDetailsUiState.Error("Error: No se pudieron obtener los detalles del vehículo.")
                }
            }
        }
    }

    fun onGetVehicleMaintenances(vehicleId: String) {
        viewModelScope.launch {
            when (val result = getMaintenancesUseCase(vehicleId)) {
                is GetMaintenancesResult.Success -> {
                    _uiStateMaintenance.value = if (result.maintenances.isNullOrEmpty()) {
                        VehicleMaintenanceUiState.Empty
                    } else {
                        println("Mantenimientos: ${result.maintenances}")
                        VehicleMaintenanceUiState.Loaded(result.maintenances.map { it.toUiModel() })
                    }
                }

                is GetMaintenancesResult.Error -> {
                    _uiStateMaintenance.value = VehicleMaintenanceUiState.Empty
                }
            }
        }
    }
}