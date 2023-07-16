package com.example.android.waterwise.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.datastore.UserPreferencesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingUiState(goalHydrationAmount = 2000))
    val uiState: StateFlow<SettingUiState> = _uiState

    init {
        fetchGoalHydrationAmount()
    }

    private fun fetchGoalHydrationAmount() {
        viewModelScope.launch {
            val goalHydrationAmount = userPreferencesRepository.getGoalHydrationAmount()
            _uiState.value = _uiState.value.copy(goalHydrationAmount = goalHydrationAmount)
        }
    }

    fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setGoalHydrationAmount(goalHydrationAmount)
            _uiState.value = _uiState.value.copy(goalHydrationAmount = goalHydrationAmount)
        }
    }
}

data class SettingUiState(val goalHydrationAmount: Int)