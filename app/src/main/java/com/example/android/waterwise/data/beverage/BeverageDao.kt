package com.example.android.waterwise.data.beverage

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BeverageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(beverage: Beverage)

    @Update
    suspend fun update(beverage: Beverage)

    @Delete
    suspend fun delete(beverage: Beverage)

    @Query("SELECT * FROM beverage")
    fun getAllBeverage(): Flow<List<Beverage>>

    @Transaction
    @Query("SELECT * FROM beverage")
    fun getAllBeverageWithHydratePresets(): Flow<List<BeverageWithHydratePresets>>

    @Transaction
    @Query("SELECT * FROM beverage WHERE beverage_id = :id")
    fun getBeverageWithHydratePresetsById(id: Long): Flow<BeverageWithHydratePresets>
}