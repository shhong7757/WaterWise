package com.example.android.waterwise.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.waterwise.data.beverage.Beverage
import com.example.android.waterwise.data.beverage.BeverageDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecord
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratepreset.HydratePreset
import com.example.android.waterwise.data.hydratepreset.HydratePresetDao

@Database(
    entities = [Beverage::class, HydratePreset::class, HydratedRecord::class],
    version = 1,
    exportSchema = false
)
abstract class WaterWiseDatabase : RoomDatabase() {
    abstract fun beverageDao(): BeverageDao
    abstract fun hydratedRecordDao(): HydratedRecordDao
    abstract fun hydratePresetDao(): HydratePresetDao
}