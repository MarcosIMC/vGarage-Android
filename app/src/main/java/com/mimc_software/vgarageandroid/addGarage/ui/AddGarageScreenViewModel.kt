package com.mimc_software.vgarageandroid.addGarage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addGarage.domain.AddGarageResult
import com.mimc_software.vgarageandroid.addGarage.domain.AddGarageUseCase
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed class AddGarageUiState {
    object Idle: AddGarageUiState()
    object Loading: AddGarageUiState()
    object NavigateToMain: AddGarageUiState()
    data class Error(val message: String): AddGarageUiState()
}
@HiltViewModel
class AddGarageScreenViewModel @Inject constructor(
    private val addGarageUseCase: AddGarageUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<AddGarageUiState>(AddGarageUiState.Idle)
    val uiState: StateFlow<AddGarageUiState> = _uiState
    @OptIn(ExperimentalUuidApi::class)
    fun onGarageCreated(garageName: String) {
        viewModelScope.launch {
            _uiState.value = AddGarageUiState.Loading

            when (val result = addGarageUseCase(GarageModel(
                uid = Uuid.random().toString(),
                name = garageName,
                active = true
            ))) {
                is AddGarageResult.Success -> {
                    _uiState.value = AddGarageUiState.NavigateToMain
                }
                is AddGarageResult.Duplicate -> {
                    _uiState.value = AddGarageUiState.Error("El garaje ya existe.")
                }
                is AddGarageResult.Error -> {
                    _uiState.value = AddGarageUiState.Error("Error: ${result.exception}")
                }
            }

        }
    }
}