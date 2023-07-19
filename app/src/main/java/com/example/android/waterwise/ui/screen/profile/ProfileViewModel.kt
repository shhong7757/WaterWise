package com.example.android.waterwise.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.model.Sex
import com.example.android.waterwise.ui.screen.setting.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesRepository: DataStoreUserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingUiState(
            goalHydrationAmount = 2000, height = null, weight = null, sex = null
        )
    )
    val uiState: StateFlow<SettingUiState> = _uiState

    init {
        fetUserProfile()
    }

    private fun fetUserProfile() {
        viewModelScope.launch {
            userPreferencesRepository.getUserProfile().collect {
                _uiState.value = _uiState.value.copy(
                    goalHydrationAmount = it.goalHydrationAmount,
                    height = it.height,
                    weight = it.width,
                    sex = it.sex
                )
            }
        }
    }

    fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setGoalHydrationAmount(goalHydrationAmount)
            _uiState.value = _uiState.value.copy(goalHydrationAmount = goalHydrationAmount)
        }
    }

    fun setUserHeight(height: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setUserHeight(height)
        }
    }

    fun setUserSex(sex: Sex) {
        viewModelScope.launch {
            userPreferencesRepository.setUserSex(sex)
            _uiState.value = _uiState.value.copy(sex = sex)
        }
    }

    fun setUserWeight(height: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setUserWeight(height)
        }
    }
}