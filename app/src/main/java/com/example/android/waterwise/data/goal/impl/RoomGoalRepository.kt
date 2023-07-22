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
import java.util.*
import javax.inject.Inject

class RoomGoalRepository @Inject constructor(
    private val dateRecordDao: DateRecordDao, private val goalDao: GoalDao
) : GoalRepository {
    override suspend fun insert(goal: Int): Unit = withContext(Dispatchers.IO) {
        val now = LocalDateTime.now()
        val date = convertToLocalDateTimeToDate(
            getStartOfDay(now)
        )
        val goalId = goalDao.insert(Goal(date = date, value = goal))
        val dateRecord = dateRecordDao.getDateRecord(date)
        if (dateRecord == null) {
            dateRecordDao.insert(
                DateRecord(
                    date = date, goalId = goalId
                )
            )
        } else {
            dateRecordDao.update(
                DateRecord(
                    id = dateRecord.id, goalId = goalId, date = date
                )
            )
        }
    }

    override suspend fun getLastGoal(): Goal? = goalDao.getLastGoal()

    override fun getGoalBeforeDate(date: Date): Flow<Goal?> = goalDao.getGoalBeforeDate(date)

    override fun getLastGoalFlow(): Flow<Goal?> = goalDao.getLastGoalFlow()

    override fun getGoal(id: Long): Flow<Goal> = goalDao.getGoal(id)
}