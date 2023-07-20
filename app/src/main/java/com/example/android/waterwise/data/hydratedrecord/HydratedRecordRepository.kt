package com.example.android.waterwise.data.hydratedrecord

import kotlinx.coroutines.flow.Flow
import java.util.*

interface HydratedRecordRepository {
    suspend fun insertHydratedRecord(hydratedRecord: HydratedRecord)
    suspend fun updateHydratedRecord(hydratedRecord: HydratedRecord)
    suspend fun deleteHydratedRecord(hydratedRecord: HydratedRecord)
    fun getAllHydratedRecordByDate(start: Date, end: Date): Flow<List<HydratedRecord>>
    fun getAllHydratedRecordAndBeverageByDate(
        start: Date, end: Date
    ): Flow<List<HydratedRecordAndBeverage>>
}