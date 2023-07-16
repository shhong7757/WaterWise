package com.example.android.waterwise.data

interface UserPreferencesRepository {
    suspend fun getGoalHydrationAmount(): Int
    suspend fun setGoalHydrationAmount(goalHydrationAmount: Int)
}
