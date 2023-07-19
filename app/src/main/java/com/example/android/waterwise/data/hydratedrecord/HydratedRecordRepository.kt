package com.example.android.waterwise.data.hydratedrecord

import kotlinx.coroutines.flow.Flow

interface HydratedRecordRepository {
    suspend fun insertHydratedRecord(hydratedRecord: HydratedRecord)
    suspend fun updateHydratedRecord(hydratedRecord: HydratedRecord)
    suspend fun deleteHydratedRecord(hydratedRecord: HydratedRecord)
    fun getAllHydratedRecordByDate(date: String): Flow<List<HydratedRecord>>
    fun getAllHydratedRecordAndBeverageByDate(date: String): Flow<List<HydratedRecordAndBeverage>>
}