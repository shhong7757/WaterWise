package com.example.android.waterwise.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.android.datastore.SerializedUserProfile
import com.example.android.waterwise.data.UserPreferencesRepository
import com.example.android.waterwise.data.UserProfile
import com.example.android.waterwise.model.Sex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val userProfileStore: DataStore<SerializedUserProfile>
) : UserPreferencesRepository {

    private object PreferencesKeys {
        val HAS_SHOW_ONGOING_SCREEN_BEFORE = booleanPreferencesKey("has_show_ongoing_screen_before")
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

    override fun getUserProfile(): Flow<UserProfile> {
        return userProfileStore.data.catch { exception ->
            if (exception is IOException) {
                emit(SerializedUserProfile.getDefaultInstance())
            } else {
                throw exception
            }
        }.map {
            UserProfile(
                goalHydrationAmount = it.goalHydrationAmount, sex = when (it.sex) {
                    1 -> Sex.Man
                    2 -> Sex.Woman
                    else -> null
                }, height = it.height, width = it.width
            )
        }
    }

    override suspend fun setGoalHydrationAmount(goalHydrationAmount: Int) {
        userProfileStore.updateData { preferences ->
            preferences.toBuilder().setGoalHydrationAmount(goalHydrationAmount).build()
        }
    }

    override suspend fun setHasShowOngoingScreenBefore(hasShowOngoingScreenBefore: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HAS_SHOW_ONGOING_SCREEN_BEFORE] = hasShowOngoingScreenBefore
        }
    }

    override suspend fun setUserProfile(userProfile: UserProfile) {
        userProfileStore.updateData { preferences ->
            preferences.toBuilder().setGoalHydrationAmount(userProfile.goalHydrationAmount)
                .setHeight(userProfile.height ?: 0).setSex(
                    when (userProfile.sex) {
                        Sex.Man -> 1
                        Sex.Woman -> 2
                        else -> 0
                    }
                ).setWidth(userProfile.width ?: 0).build()
        }
    }

    override suspend fun setUserHeight(height: Int) {
        userProfileStore.updateData { preferences ->
            preferences.toBuilder().setHeight(height).build()
        }
    }

    override suspend fun setUserSex(sex: Sex) {
        userProfileStore.updateData { preferences ->
            preferences.toBuilder().setSex(sex.let {
                when (it) {
                    Sex.Man -> 1
                    Sex.Woman -> 2
                }
            }).build()
        }
    }

    override suspend fun setUserWeight(weight: Int) {
        userProfileStore.updateData { preferences ->
            preferences.toBuilder().setWidth(weight).build()
        }
    }
}