package com.example.android.waterwise.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.goal.impl.RoomGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val goalRepository: RoomGoalRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingUiState(
            goalHydrationAmount = 2000
        )
    )
    val uiState: StateFlow<SettingUiState> = _uiState

    init {
        fetchGoal()
    }

    private fun fetchGoal() {
        viewModelScope.launch {
            goalRepository.getLastGoalFlow().collect() {
                _uiState.value = _uiState.value.copy(goalHydrationAmount = it?.value ?: 0)
            }
        }
    }
}

data class SettingUiState(
    val goalHydrationAmount: Int
)