package com.example.android.waterwise.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DailyHydrationRecord::class], version = 1, exportSchema = false)
abstract class WaterWiseDatabase : RoomDatabase() {
    abstract fun dailyHydrationRecordDao(): DailyHydrationRecordDao

    companion object {
        @Volatile
        private var Instance: WaterWiseDatabase? = null

        fun getDatabase(context: Context): WaterWiseDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WaterWiseDatabase::class.java, "water_wise_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}