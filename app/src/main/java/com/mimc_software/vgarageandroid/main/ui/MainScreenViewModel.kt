package com.mimc_software.vgarageandroid.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.main.data.toUiModel
import com.mimc_software.vgarageandroid.main.domain.GetVehiclesByGarageUseCase
import com.mimc_software.vgarageandroid.main.domain.GetVehiclesResult
import com.mimc_software.vgarageandroid.main.ui.model.VehicleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class GetVehiclesUiState {
    object Idle : GetVehiclesUiState()
    object Loading : GetVehiclesUiState()
    data class Loaded(val vehicles: List<VehicleModel>) : GetVehiclesUiState()
    object Empty : GetVehiclesUiState()
    data class Error(val message: String) : GetVehiclesUiState()
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getVehiclesByGarageUseCase: GetVehiclesByGarageUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<GetVehiclesUiState>(GetVehiclesUiState.Idle)
    val uiState: StateFlow<GetVehiclesUiState> = _uiState
    @OptIn(ExperimentalStdlibApi::class)
    fun onGetVehicles(garageId: String) {
        viewModelScope.launch {
            _uiState.value = GetVehiclesUiState.Loading

            when(val result = getVehiclesByGarageUseCase(garageId)) {
                is GetVehiclesResult.Success -> {
                    _uiState.value = if (result.vehicles.isNullOrEmpty()) {
                        GetVehiclesUiState.Empty
                    } else {
                        GetVehiclesUiState.Loaded(result.vehicles.map { it.toUiModel() })
                    }
                }
                else -> {
                    _uiState.value = GetVehiclesUiState.Error("Error: No se pudieron obtener los vehículos.")
                }
            }
        }
    }
}