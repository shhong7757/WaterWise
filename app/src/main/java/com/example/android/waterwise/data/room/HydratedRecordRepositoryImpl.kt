package com.example.android.waterwise.data.room

import com.example.android.waterwise.data.HydratedRecord
import com.example.android.waterwise.data.HydratedRecordAndBeverage
import com.example.android.waterwise.data.HydratedRecordDao
import com.example.android.waterwise.data.HydratedRecordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HydratedRecordRepositoryImpl @Inject constructor(
    private val hydratedRecordDao: HydratedRecordDao
) : HydratedRecordRepository {
    override suspend fun insertHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.insert(hydratedRecord)


    override suspend fun updateHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.update(hydratedRecord)


    override suspend fun deleteHydratedRecord(hydratedRecord: HydratedRecord) =
        hydratedRecordDao.delete(hydratedRecord)


    override fun getAllHydratedRecordByDate(date: String): Flow<List<HydratedRecord>> =
        hydratedRecordDao.getAllHydratedRecordByDate(date)

    override fun getAllHydratedRecordAndBeverageByDate(date: String): Flow<List<HydratedRecordAndBeverage>> =
        hydratedRecordDao.getAllHydratedRecordAndBeverageByDate(date)
}