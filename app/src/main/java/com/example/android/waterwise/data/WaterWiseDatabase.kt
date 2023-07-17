package com.example.android.waterwise.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Beverage::class, HydratePreset::class, DailyHydrationRecord::class],
    version = 1,
    exportSchema = false
)
abstract class WaterWiseDatabase : RoomDatabase() {
    abstract fun beverageDao(): BeverageDao
    abstract fun dailyHydrationRecordDao(): DailyHydrationRecordDao
    abstract fun hydratePresetDao(): HydratePresetDao
}