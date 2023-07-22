package com.example.android.waterwise.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.goal.Goal
import com.example.android.waterwise.data.goal.impl.RoomGoalRepository
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.model.Sex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val goalRepository: RoomGoalRepository,
    private val userPreferencesRepository: DataStoreUserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ProfileUiState(
            goalHydrationAmount = 2000, height = null, weight = null, sex = null
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        fetchGoal()
        fetchUserProfile()
    }

    private fun fetchGoal() {
        viewModelScope.launch {
            goalRepository.getLastGoalFlow().collect() {
                _uiState.value = _uiState.value.copy(goalHydrationAmount = it.value)
            }
        }
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            userPreferencesRepository.getUserProfile().collect {
                _uiState.value = _uiState.value.copy(
                    height = it.height, weight = it.width, sex = it.sex
                )
            }
        }
    }

    fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        viewModelScope.launch {
            goalRepository.insert(Goal(value = goalHydrationAmount))
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

data class ProfileUiState(
    val goalHydrationAmount: Int, val height: Int?, val weight: Int?, val sex: Sex?
)