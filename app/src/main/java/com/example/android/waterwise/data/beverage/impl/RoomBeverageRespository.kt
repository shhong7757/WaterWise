package com.example.android.waterwise.data.room

import com.example.android.waterwise.data.beverage.Beverage
import com.example.android.waterwise.data.beverage.BeverageDao
import com.example.android.waterwise.data.beverage.BeverageRepository
import com.example.android.waterwise.data.beverage.BeverageWithHydratePresets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeverageRepositoryImpl @Inject constructor(
    private val beverageDao: BeverageDao
) : BeverageRepository {
    override suspend fun insertBeverage(beverage: Beverage) = beverageDao.insert(beverage)

    override suspend fun updateBeverage(beverage: Beverage) = beverageDao.update(beverage)

    override suspend fun deleteBeverage(beverage: Beverage) = beverageDao.delete(beverage)

    override fun getAllBeverageStream(): Flow<List<Beverage>> = beverageDao.getAllBeverage()

    override fun getAllBeverageWithHydratePresetsStream(): Flow<List<BeverageWithHydratePresets>> =
        beverageDao.getAllBeverageWithHydratePresets()

    override fun getBeverageWithHydratePresetsById(id: Long): Flow<BeverageWithHydratePresets> =
        beverageDao.getBeverageWithHydratePresetsById(id)
}