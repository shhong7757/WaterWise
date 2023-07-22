package com.example.android.waterwise.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.goal.Goal
import com.example.android.waterwise.data.goal.impl.RoomGoalRepository
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.data.profile.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val goalRepository: RoomGoalRepository,
    private val userPreferencesRepository: DataStoreUserPreferencesRepository
) : ViewModel() {
    fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore)
        }
    }

    fun setUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            goalRepository.insert(Goal(value = userProfile.goalHydrationAmount))
            userPreferencesRepository.setUserProfile(userProfile)
        }
    }
}