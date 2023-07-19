package com.example.android.waterwise.data.preferences

import com.example.android.waterwise.data.profile.UserProfile
import com.example.android.waterwise.model.Sex
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    // getter
    fun getHasShowOngoingScreenBefore(): Flow<Boolean>
    fun getUserProfile(): Flow<UserProfile>

    // setter
    suspend fun setGoalHydrationAmount(goalHydrationAmount: Int)
    suspend fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean)
    suspend fun setUserProfile(userProfile: UserProfile)
    suspend fun setUserHeight(height: Int)
    suspend fun setUserSex(sex: Sex)
    suspend fun setUserWeight(weight: Int)
}
