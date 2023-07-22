package com.example.android.waterwise.data.hydratedrecord.impl

import com.example.android.waterwise.data.dateRecord.DateRecord
import com.example.android.waterwise.data.dateRecord.DateRecordDao
import com.example.android.waterwise.data.goal.GoalDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecord
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordRepository
import com.example.android.waterwise.util.convertToLocalDateTimeToDate
import com.example.android.waterwise.util.getStartOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class RoomHydratedRecordRepository @Inject constructor(
    private val dateRecordDao: DateRecordDao,
    private val goalDao: GoalDao,
    private val hydratedRecordDao: HydratedRecordDao
) : HydratedRecordRepository {
    override suspend fun insertHydratedRecord(amount: Int, beverage_id: Long) =
        withContext(Dispatchers.IO) {
            val now = LocalDateTime.now()
            val goal = goalDao.getLastGoal()
            val startOfDay = convertToLocalDateTimeToDate(
                getStartOfDay(now)
            )

            if (goal != null) {
                val dateRecordId = when (val dateRecord = dateRecordDao.getDateRecord(startOfDay)) {
                    null -> dateRecordDao.insert(
                        DateRecord(
                            date = startOfDay, goalId = goal.id
                        )
                    )
                    else -> dateRecord.id
                }
                hydratedRecordDao.insert(
                    HydratedRecord(
                        amount = amount,
                        beverageId = beverage_id,
                        dateRecordId = dateRecordId,
                        date = convertToLocalDateTimeToDate(now)
                    )
                )
            }
        }

    override suspend fun updateHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.update(hydratedRecord)

    override suspend fun deleteHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.delete(hydratedRecord)
}
