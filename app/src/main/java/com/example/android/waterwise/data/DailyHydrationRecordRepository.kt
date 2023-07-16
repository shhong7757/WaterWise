package com.example.android.waterwise.data

import kotlinx.coroutines.flow.Flow

interface  DailyHydrationRecordRepository {
    suspend fun insertDailyHydrationRecord(hydrationRecord: DailyHydrationRecord)
    suspend fun updateDailyHydrationRecord(hydrationRecord: DailyHydrationRecord)
    suspend fun deleteDailyHydrationRecord(hydrationRecord: DailyHydrationRecord)
    fun getAllHydrationAmountStream(date: String): Flow<List<DailyHydrationRecord>>
}