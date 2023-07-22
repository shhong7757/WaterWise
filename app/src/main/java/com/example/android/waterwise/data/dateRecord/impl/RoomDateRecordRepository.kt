package com.example.android.waterwise.data.dateRecord.impl

import com.example.android.waterwise.data.dateRecord.*
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class RoomDateRecordRepository @Inject constructor(
    private val dateRecordDao: DateRecordDao
) : DateRecordRepository {
    override suspend fun insertDateRecord(dateRecord: DateRecord): Long {
        return dateRecordDao.insert(dateRecord)
    }

    override suspend fun updateDateRecord(dateRecord: DateRecord) = dateRecordDao.update(dateRecord)

    override suspend fun getDateRecord(date: Date): DateRecord? {
        return dateRecordDao.getDateRecord(date)
    }

    override fun getDateRecordAndGoalWithHydratedRecords(date: Date) =
        dateRecordDao.getDateRecordAndGoalWithHydratedRecords(date)

    override fun getDateRecordAndGoalWithHydratedRecordAndBeverageList(date: Date) =
        dateRecordDao.getDateRecordAndGoalWithHydratedRecordAndBeverageList(date)
}