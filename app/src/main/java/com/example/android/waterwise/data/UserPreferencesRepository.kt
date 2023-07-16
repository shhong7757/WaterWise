package com.example.android.waterwise.data

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun getGoalHydrationAmount(): Int
    fun getHasShowOngoingScreenBefore(): Flow<Boolean>
    suspend fun setGoalHydrationAmount(goalHydrationAmount: Int)
    suspend fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean)
}
