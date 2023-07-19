package com.example.android.waterwise.data.hydratepreset.impl

import com.example.android.waterwise.data.hydratepreset.HydratePreset
import com.example.android.waterwise.data.hydratepreset.HydratePresetDao
import com.example.android.waterwise.data.hydratepreset.HydratePresetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomHydratePresetRepository @Inject constructor(
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