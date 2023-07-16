package com.example.android.waterwise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.android.datastore.SerializedUserProfile
import com.example.android.waterwise.DATA_STORE_FILE_NAME
import com.example.android.waterwise.USER_PREFERENCES_NAME
import com.example.android.waterwise.data.DailyHydrationRecordDao
import com.example.android.waterwise.data.UserProfileSerializer
import com.example.android.waterwise.data.WaterWiseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(USER_PREFERENCES_NAME)
            }
        )
    }

    @Provides
    @Singleton
    fun provideUserProfileDatStore(@ApplicationContext context: Context): DataStore<SerializedUserProfile> {
        return DataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(DATA_STORE_FILE_NAME)
            },
            serializer = UserProfileSerializer
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext contenxt: Context): WaterWiseDatabase {
        return Room.databaseBuilder(
            contenxt,
            WaterWiseDatabase::class.java,
            "warter_wise"
        ).build()
    }

    @Provides
    fun provideDailyHydrationRecordDao(database: WaterWiseDatabase): DailyHydrationRecordDao {
        return database.dailyHydrationRecordDao()
    }
}