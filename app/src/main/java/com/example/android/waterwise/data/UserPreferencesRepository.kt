package com.example.android.waterwise.data

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    // getter
    fun getHasShowOngoingScreenBefore(): Flow<Boolean>
    suspend fun getUserProfile(): UserProfile

    // setter
    suspend fun setGoalHydrationAmount(goalHydrationAmount: Int)
    suspend fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean)
    suspend fun setUserProfile(userProfile: UserProfile)
}
