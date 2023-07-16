package com.example.android.waterwise

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

@HiltAndroidApp
class WaterWiseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}