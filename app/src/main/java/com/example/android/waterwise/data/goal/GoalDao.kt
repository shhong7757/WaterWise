package com.example.android.waterwise.data.goal

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(goal:Goal): Long

    @Query("SELECT * FROM goal ORDER BY goal_id DESC LIMIT 1")
    suspend fun getLastGoal(): Goal?

    @Query("SELECT * FROM goal ORDER BY goal_id DESC LIMIT 1")
    fun getLastGoalFlow(): Flow<Goal?>

    @Query("SELECT * FROM goal WHERE goal_id = :id LIMIT 1")
    fun getGoal(id: Long): Flow<Goal>

    @Query("SELECT * FROM goal WHERE date <= :date ORDER BY goal_id DESC LIMIT 1")
    fun getGoalBeforeDate(date:Date): Flow<Goal>
}