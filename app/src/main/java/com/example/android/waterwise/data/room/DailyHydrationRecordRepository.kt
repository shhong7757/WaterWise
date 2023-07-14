package com.example.android.waterwise.data.room

import com.example.android.waterwise.data.DailyHydrationRecord
import com.example.android.waterwise.data.DailyHydrationRecordDao
import com.example.android.waterwise.data.DailyHydrationRecordRepositoryInterface
import kotlinx.coroutines.flow.Flow

class DailyHydrationRecordRepository(private val dailyHydrationRecordDao: DailyHydrationRecordDao) :
    DailyHydrationRecordRepositoryInterface {
    override suspend fun insertDailyHydrationRecord(hydrationRecord: DailyHydrationRecord) =
        dailyHydrationRecordDao.insert(hydrationRecord)


    override suspend fun updateDailyHydrationRecord(hydrationRecord: DailyHydrationRecord) =
        dailyHydrationRecordDao.update(hydrationRecord)


    override suspend fun deleteDailyHydrationRecord(hydrationRecord: DailyHydrationRecord) =
        dailyHydrationRecordDao.delete(hydrationRecord)


    override fun getAllHydrationAmountStream(date: String): Flow<List<DailyHydrationRecord>> =
        dailyHydrationRecordDao.getAllHydrationAmount(date)
}