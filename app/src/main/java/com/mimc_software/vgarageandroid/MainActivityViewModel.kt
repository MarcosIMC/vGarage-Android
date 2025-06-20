package com.mimc_software.vgarageandroid

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mimc_software.vgarageandroid.addGarage.domain.GetGarageActiveUseCase
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getGarageActiveUseCase: GetGarageActiveUseCase
): ViewModel(){
    var startDestination by mutableStateOf<String?>(null)
        private set
    var activeGarage by mutableStateOf<GarageModel?>(null)
        private set

    init {
        viewModelScope.launch {
            activeGarage = getGarageActiveUseCase.invoke()
            startDestination = if (activeGarage != null) {
                "main"
            } else {
                "landing"
            }
        }
    }
}