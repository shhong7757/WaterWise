package com.example.android.waterwise.data.room

import com.example.android.waterwise.data.HydratePreset
import com.example.android.waterwise.data.HydratePresetDao
import com.example.android.waterwise.data.HydratePresetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HydratePresetRepositoryImpl @Inject constructor(
    private val hydratePresetDao: HydratePresetDao
) : HydratePresetRepository {
    override suspend fun insertHydratePreset(beveragePreset: HydratePreset) =
        hydratePresetDao.insert(beveragePreset)

    override suspend fun updateHydratePreset(beveragePreset: HydratePreset) =
        hydratePresetDao.update(beveragePreset)

    override suspend fun deleteHydratePreset(beveragePreset: HydratePreset) =
        hydratePresetDao.delete(beveragePreset)

    override fun getAllHydratePresetStream(): Flow<List<HydratePreset>> =
        hydratePresetDao.getAllHydratePreset()
}