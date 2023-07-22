package com.example.android.waterwise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.datastore.SerializedUserProfile
import com.example.android.waterwise.DATA_STORE_FILE_NAME
import com.example.android.waterwise.USER_PREFERENCES_NAME
import com.example.android.waterwise.data.*
import com.example.android.waterwise.data.beverage.BeverageDao
import com.example.android.waterwise.data.dateRecord.DateRecordDao
import com.example.android.waterwise.data.goal.GoalDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratepreset.HydratePresetDao
import com.example.android.waterwise.data.profile.UserProfileSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataPersistenceModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile(USER_PREFERENCES_NAME)
        })
    }

    @Provides
    @Singleton
    fun provideUserProfileDatStore(@ApplicationContext context: Context): DataStore<SerializedUserProfile> {
        return DataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(DATA_STORE_FILE_NAME)
            }, serializer = UserProfileSerializer
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext contenxt: Context): WaterWiseDatabase {
        return Room.databaseBuilder(
            contenxt, WaterWiseDatabase::class.java, "water_wise"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("insert into beverage (value, color) values ('water', '0x03DAC6');")
                db.execSQL("insert into beverage (value, color) values ('coffee', '0x000000');")
                db.execSQL("insert into hydrate_preset (beverage_id, amount, nickname ) values(1, 200, 'water') ")
                db.execSQL("insert into hydrate_preset (beverage_id, amount, nickname ) values(2, 350, 'coffee') ")
            }
        }).build()
    }

    @Provides
    fun provideBeverageDao(database: WaterWiseDatabase): BeverageDao {
        return database.beverageDao()
    }

    @Provides
    fun provideDateRecordDao(database: WaterWiseDatabase): DateRecordDao {
        return database.dateRecordDao()
    }

    @Provides
    fun provideGoalDao(database: WaterWiseDatabase): GoalDao {
        return database.goalDao()
    }

    @Provides
    fun provideHydratePresetDao(database: WaterWiseDatabase): HydratePresetDao {
        return database.hydratePresetDao()
    }

    @Provides
    fun provideHydratedRecordDto(database: WaterWiseDatabase): HydratedRecordDao {
        return database.hydratedRecordDao()
    }
}