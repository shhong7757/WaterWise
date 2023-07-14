package com.example.android.waterwise.data

import android.content.Context
import com.example.android.waterwise.data.room.DailyHydrationRecordRepository

interface AppContainer {
    val dailyHydrationRecordRepository: DailyHydrationRecordRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val dailyHydrationRecordRepository: DailyHydrationRecordRepository by lazy {
        DailyHydrationRecordRepository(
            WaterWiseDatabase.getDatabase(context).dailyHydrationRecordDao()
        )
    }
}