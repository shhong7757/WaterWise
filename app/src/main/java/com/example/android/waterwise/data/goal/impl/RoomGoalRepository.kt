package com.example.android.waterwise.data.goal.impl

import com.example.android.waterwise.data.dateRecord.DateRecord
import com.example.android.waterwise.data.dateRecord.DateRecordDao
import com.example.android.waterwise.data.goal.Goal
import com.example.android.waterwise.data.goal.GoalDao
import com.example.android.waterwise.data.goal.GoalRepository
import com.example.android.waterwise.util.convertToLocalDateTimeToDate
import com.example.android.waterwise.util.getStartOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class RoomGoalRepository @Inject constructor(
    private val dateRecordDao: DateRecordDao, private val goalDao: GoalDao
) : GoalRepository {
    override suspend fun insert(goal: Goal): Unit = withContext(Dispatchers.IO) {
        val now = LocalDateTime.now()
        val startOfDay = convertToLocalDateTimeToDate(
            getStartOfDay(now)
        )
        val goalId = goalDao.insert(goal)
        val dateRecord = dateRecordDao.getDateRecord(startOfDay)
        if (dateRecord == null) {
            dateRecordDao.insert(
                DateRecord(
                    date = startOfDay, goalId = goalId
                )
            )
        } else {
            dateRecordDao.update(
                DateRecord(
                    id = dateRecord.id, goalId = goalId, date = startOfDay
                )
            )
        }
    }

    override suspend fun getLastGoal(): Goal = goalDao.getLastGoal()

    override fun getLastGoalFlow(): Flow<Goal> = goalDao.getLastGoalFlow()

    override fun getGoal(id: Long): Flow<Goal> = goalDao.getGoal(id)
}