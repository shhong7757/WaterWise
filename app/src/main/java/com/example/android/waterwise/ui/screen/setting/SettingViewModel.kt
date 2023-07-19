package com.example.android.waterwise.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.model.Sex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
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
}

data class SettingUiState(
    val goalHydrationAmount: Int, val height: Int?, val weight: Int?, val sex: Sex?
)