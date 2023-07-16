package com.example.android.waterwise.ui.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.datastore.UserPreferencesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepositoryImpl
) : ViewModel() {
    fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore)
        }
    }
}