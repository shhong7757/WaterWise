package com.example.android.waterwise.ui.screen.beveragepresetmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.Beverage
import com.example.android.waterwise.data.BeverageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeveragePresetManagementViewModel @Inject constructor(private val beverageRepository: BeverageRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(
        PresetManagementUiState()
    )
    val uiState: StateFlow<PresetManagementUiState> = _uiState

    init {
        fetchBeverages()
    }

    private fun fetchBeverages() {
        viewModelScope.launch {
            beverageRepository.getAllBeverageStream().collect { beverages ->
                beverages.let {
                    _uiState.value = _uiState.value.copy(beverages = it)
                }
            }
        }
    }
}

data class PresetManagementUiState(
    val beverages: List<Beverage> = listOf()
)
