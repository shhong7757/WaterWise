package com.example.android.waterwise.data.hydratepreset

import kotlinx.coroutines.flow.Flow

interface HydratePresetRepository {
    suspend fun insertHydratePreset(beveragePreset: HydratePreset)
    suspend fun updateHydratePreset(beveragePreset: HydratePreset)
    suspend fun deleteHydratePreset(beveragePreset: HydratePreset)
    fun getAllHydratePresetStream(): Flow<List<HydratePreset>>
}