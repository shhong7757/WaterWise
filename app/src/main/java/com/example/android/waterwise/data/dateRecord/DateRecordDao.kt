package com.example.android.waterwise.data.dateRecord

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DateRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(date: DateRecord): Long

    @Update
    suspend fun update(date: DateRecord)

    @Query("SELECT * FROM date_record WHERE date = :date")
    suspend fun getDateRecord(date: Date): DateRecord?

    @Transaction
    @Query("SELECT * FROM date_record WHERE date = :date LIMIT 1")
    fun getDateRecordWithHydratedRecords(date: Date): Flow<DateRecordWithHydratedRecords>

    @Transaction
    @Query("SELECT * FROM date_record WHERE date = :date LIMIT 1")
    fun getDateRecordAndGoalWithHydratedRecords(date: Date): Flow<DateRecordAndGoalWithHydratedRecords?>

    @Transaction
    @Query("SELECT * FROM date_record WHERE date = :date LIMIT 1")
    fun getDateRecordAndGoalWithHydratedRecordAndBeverageList(date: Date): Flow<DateRecordAndGoalWithHydratedRecordAndBeverageList?>
}