package com.example.android.waterwise

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.android.datastore.SerializedUserProfile
import com.example.android.waterwise.data.profile.UserProfileSerializer
import dagger.hilt.android.HiltAndroidApp

const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

const val DATA_STORE_FILE_NAME = "user_profile.pb"
private val Context.userProfileStore: DataStore<SerializedUserProfile> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserProfileSerializer
)

@HiltAndroidApp
class WaterWiseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}