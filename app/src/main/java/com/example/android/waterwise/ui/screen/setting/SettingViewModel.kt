package com.example.android.waterwise.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.datastore.UserPreferencesRepositoryImpl
import com.example.android.waterwise.model.Sex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingUiState(
            goalHydrationAmount = 2000,
            height = null,
            weight = null,
            sex = null
        )
    )
    val uiState: StateFlow<SettingUiState> = _uiState

    init {
        fetUserProfile()
    }

    private fun fetUserProfile() {
        viewModelScope.launch {
            val userProfile = userPreferencesRepository.getUserProfile()
            _uiState.value = _uiState.value.copy(
                goalHydrationAmount = userProfile.goalHydrationAmount,
                height = userProfile.height,
                weight = userProfile.width,
                sex = userProfile.sex
            )
        }
    }

    fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setGoalHydrationAmount(goalHydrationAmount)
            _uiState.value = _uiState.value.copy(goalHydrationAmount = goalHydrationAmount)
        }
    }
}

data class SettingUiState(
    val goalHydrationAmount: Int, val height: Int?, val weight: Int?, val sex: Sex?
)