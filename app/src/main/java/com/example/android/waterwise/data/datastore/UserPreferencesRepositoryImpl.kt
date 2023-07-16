package com.example.android.waterwise.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.android.waterwise.data.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    private object PreferencesKeys {
        val GOAL_HYDRATION_AMOUNT = intPreferencesKey("goal_hydration_amount")
        val HAS_SHOW_ONGOING_SCREEN_BEFORE = booleanPreferencesKey("has_show_ongoing_screen_before")
    }

    override suspend fun getGoalHydrationAmount(): Int {
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

    override fun getHasShowOngoingScreenBefore(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.HAS_SHOW_ONGOING_SCREEN_BEFORE] ?: false
        }
    }

    override suspend fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.GOAL_HYDRATION_AMOUNT] = goalHydrationAmount
        }
    }

    override suspend fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HAS_SHOW_ONGOING_SCREEN_BEFORE] = hasShowOngoingScreenBefore
        }
    }
}