package com.mimc_software.vgarageandroid.addMaintenance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addMaintenance.domain.AddMaintenanceResult
import com.mimc_software.vgarageandroid.addMaintenance.domain.AddMaintenanceUseCase
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.MaintenanceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AddMaintenanceUiState {
    object Idle: AddMaintenanceUiState()
    object Loading: AddMaintenanceUiState()
    object Success: AddMaintenanceUiState()
    data class Error(val exception: Throwable): AddMaintenanceUiState()
}

data class AddMaintenanceDataUiState(
    val maintenanceTitle: String = "",
    val maintenanceType: String = "",
    val maintenanceDate: Long? = 0,
    val maintenancePrice: Long? = 0,
    val maintenanceNotes: String = "",
    val vehicleId: String = "",
)

@HiltViewModel
class AddMaintenanceViewModel @Inject constructor(
    private val addMaintenanceUseCase: AddMaintenanceUseCase
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow<AddMaintenanceUiState>(AddMaintenanceUiState.Idle)
    val uiStateFlow: StateFlow<AddMaintenanceUiState> = _uiStateFlow
    private val _uiState = MutableStateFlow(AddMaintenanceDataUiState())
    val uiState: StateFlow<AddMaintenanceDataUiState> = _uiState

    val maintenanceTitleHasError: Boolean
        get() = uiState.value.maintenanceTitle.isEmpty() || "[0-9]".toRegex()
            .containsMatchIn(uiState.value.maintenanceTitle)

    fun onMaintenanceTitleChange(newValue: String) {
        _uiState.value = _uiState.value.copy(maintenanceTitle = newValue)
    }

    fun onMaintenanceDateChange(newValue: Long?) {
        _uiState.value = _uiState.value.copy(maintenanceDate = newValue)
    }

    fun onMaintenancePriceChange(newValue: Long?) {
        _uiState.value = _uiState.value.copy(maintenancePrice = newValue)
    }

    fun onMaintenanceNotesChange(newValue: String) {
        _uiState.value = _uiState.value.copy(maintenanceNotes = newValue)
    }

    fun onAddMaintenance(newMaintenance: MaintenanceModel) {
        viewModelScope.launch {
            _uiStateFlow.value = AddMaintenanceUiState.Loading
            when (val result = addMaintenanceUseCase(newMaintenance)) {
                is AddMaintenanceResult.Success -> {
                    _uiStateFlow.value = AddMaintenanceUiState.Success
                }

                is AddMaintenanceResult.Duplicate -> {
                    _uiStateFlow.value = AddMaintenanceUiState.Error(Exception("El mantenimiento ya existe"))
                }

                is AddMaintenanceResult.Error -> {
                    _uiStateFlow.value = AddMaintenanceUiState.Error(result.exception)
                }
            }
        }
    }

    fun validateForm(): Boolean {
        return !maintenanceTitleHasError
    }
}