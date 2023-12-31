package com.example.android.waterwise.data.goal

import kotlinx.coroutines.flow.Flow
import java.util.*

interface GoalRepository {
    suspend fun insert(goal: Int)
    suspend fun getLastGoal(): Goal?
    fun getLastGoalFlow(): Flow<Goal?>
    fun getGoal(id: Long): Flow<Goal>
    fun getGoalBeforeDate(date: Date): Flow<Goal?>
}