package com.example.android.waterwise.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HydratePresetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hydratePreset: HydratePreset)

    @Update
    suspend fun update(hydratePreset: HydratePreset)

    @Delete
    suspend fun delete(hydratePreset: HydratePreset)

    @Query("SELECT * from hydrate_preset")
    fun getAllHydratePreset(): Flow<List<HydratePreset>>
}