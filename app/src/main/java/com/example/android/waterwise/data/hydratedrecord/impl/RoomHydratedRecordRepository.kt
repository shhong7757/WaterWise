package com.example.android.waterwise.data.hydratedrecord.impl

import com.example.android.waterwise.data.hydratedrecord.HydratedRecord
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordAndBeverage
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class RoomHydratedRecordRepository @Inject constructor(
    private val hydratedRecordDao: HydratedRecordDao
) : HydratedRecordRepository {
    override suspend fun insertHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.insert(hydratedRecord)


    override suspend fun updateHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.update(hydratedRecord)


    override suspend fun deleteHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.delete(hydratedRecord)


    override fun getAllHydratedRecordByDate(start: Date, end: Date): Flow<List<HydratedRecord>> =
        hydratedRecordDao.getAllHydratedRecordByDate(start, end)

    override fun getAllHydratedRecordAndBeverageByDate(
        start: Date, end: Date
    ): Flow<List<HydratedRecordAndBeverage>> =
        hydratedRecordDao.getAllHydratedRecordAndBeverageByDate(start, end)
}