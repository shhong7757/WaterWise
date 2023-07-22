package com.example.android.waterwise.data.hydratedrecord

import androidx.room.*

@Dao
interface HydratedRecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hydratedRecord: HydratedRecord)

    @Update
    suspend fun update(hydratedRecord: HydratedRecord)

    @Delete
    suspend fun delete(hydratedRecord: HydratedRecord)
}