package com.example.android.waterwise.data.dateRecord

import kotlinx.coroutines.flow.Flow
import java.util.*

interface DateRecordRepository {
    suspend fun insertDateRecord(dateRecord: DateRecord): Long
    suspend fun updateDateRecord(dateRecord: DateRecord)
    suspend fun getDateRecord(date: Date): DateRecord?
    fun getDateRecordAndGoalWithHydratedRecords(date: Date): Flow<DateRecordAndGoalWithHydratedRecords?>
    fun getDateRecordAndGoalWithHydratedRecordAndBeverageList(date: Date): Flow<DateRecordAndGoalWithHydratedRecordAndBeverageList?>
}