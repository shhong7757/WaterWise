package com.example.android.waterwise

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.android.waterwise.data.AppContainer
import com.example.android.waterwise.data.AppDataContainer
import dagger.hilt.android.HiltAndroidApp

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

@HiltAndroidApp
class WaterWiseApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this, dataStore)
    }
}