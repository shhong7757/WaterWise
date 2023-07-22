package com.example.android.waterwise.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.waterwise.data.beverage.Beverage
import com.example.android.waterwise.data.beverage.BeverageDao
import com.example.android.waterwise.data.dateRecord.DateRecord
import com.example.android.waterwise.data.dateRecord.DateRecordDao
import com.example.android.waterwise.data.goal.Goal
import com.example.android.waterwise.data.goal.GoalDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecord
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratepreset.HydratePreset
import com.example.android.waterwise.data.hydratepreset.HydratePresetDao

@Database(
    entities = [Beverage::class, DateRecord::class, Goal::class, HydratePreset::class, HydratedRecord::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WaterWiseDatabase : RoomDatabase() {
    abstract fun beverageDao(): BeverageDao
    abstract fun dateRecordDao(): DateRecordDao
    abstract fun goalDao(): GoalDao
    abstract fun hydratedRecordDao(): HydratedRecordDao
    abstract fun hydratePresetDao(): HydratePresetDao
}