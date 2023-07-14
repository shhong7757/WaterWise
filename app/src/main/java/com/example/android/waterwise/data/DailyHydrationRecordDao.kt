package com.example.android.waterwise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DailyHydrationRecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dailyHydrationRecord: DailyHydrationRecord)

    @Update
    suspend fun update(dailyHydrationRecord: DailyHydrationRecord)

    @Delete
    suspend fun delete(dailyHydrationRecord: DailyHydrationRecord)

    @Query("SELECT * from daily_hydration_record WHERE date = :date")
    fun getAllHydrationAmount(date: String): Flow<List<DailyHydrationRecord>>
}