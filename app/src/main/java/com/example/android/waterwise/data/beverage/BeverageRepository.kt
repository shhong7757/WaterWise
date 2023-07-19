package com.example.android.waterwise.data.beverage

import kotlinx.coroutines.flow.Flow

interface BeverageRepository {
    suspend fun insertBeverage(beverage: Beverage)
    suspend fun updateBeverage(beverage: Beverage)
    suspend fun deleteBeverage(beverage: Beverage)
    fun getAllBeverageStream(): Flow<List<Beverage>>
    fun getAllBeverageWithHydratePresetsStream(): Flow<List<BeverageWithHydratePresets>>
    fun getBeverageWithHydratePresetsById(id: Long): Flow<BeverageWithHydratePresets>
}