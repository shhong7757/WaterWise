package com.example.android.waterwise.data.hydratedrecord

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HydratedRecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hydratedRecord: HydratedRecord)

    @Update
    suspend fun update(hydratedRecord: HydratedRecord)

    @Delete
    suspend fun delete(hydratedRecord: HydratedRecord)

    @Query("SELECT * from hydrated_record WHERE date >= :start AND date <= :end")
    fun getAllHydratedRecordByDate(start: Date, end: Date): Flow<List<HydratedRecord>>

    @Transaction
    @Query("SELECT * from hydrated_record WHERE date >= :start AND date <= :end")
    fun getAllHydratedRecordAndBeverageByDate(
        start: Date, end: Date
    ): Flow<List<HydratedRecordAndBeverage>>
}