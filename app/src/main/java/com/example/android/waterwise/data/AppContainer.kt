package com.example.android.waterwise.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.android.waterwise.data.room.DailyHydrationRecordRepository

interface AppContainer {
    val dailyHydrationRecordRepository: DailyHydrationRecordRepository
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(private val context: Context, dataStore: DataStore<Preferences>) :
    AppContainer {
    override val dailyHydrationRecordRepository: DailyHydrationRecordRepository by lazy {
        DailyHydrationRecordRepository(
            WaterWiseDatabase.getDatabase(context).dailyHydrationRecordDao()
        )
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(dataStore)
    }
}