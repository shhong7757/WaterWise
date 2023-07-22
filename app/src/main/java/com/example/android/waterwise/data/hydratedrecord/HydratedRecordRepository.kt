package com.example.android.waterwise.data.hydratedrecord

interface HydratedRecordRepository {
    suspend fun insertHydratedRecord(amount: Int, beverage_id: Long)
    suspend fun updateHydratedRecord(hydratedRecord: HydratedRecord)
    suspend fun deleteHydratedRecord(hydratedRecord: HydratedRecord)
}