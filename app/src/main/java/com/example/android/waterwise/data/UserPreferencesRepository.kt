package com.example.android.waterwise.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val GOAL_HYDRATION_AMOUNT = intPreferencesKey("goal_hydration_amount")
    }

    suspend fun getGoalHydrationAmount(): Int {
        val flow = dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.GOAL_HYDRATION_AMOUNT] ?: 2000
        }
        
        return flow.first()
    }

    suspend fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.GOAL_HYDRATION_AMOUNT] = goalHydrationAmount
        }
    }
}
