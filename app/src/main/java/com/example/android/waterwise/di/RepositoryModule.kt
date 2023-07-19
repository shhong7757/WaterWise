package com.example.android.waterwise.di

import com.example.android.waterwise.data.*
import com.example.android.waterwise.data.room.BeverageRepositoryImpl
import com.example.android.waterwise.data.room.HydratePresetRepositoryImpl
import com.example.android.waterwise.data.room.HydratedRecordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideBeverageRepository(beverageDao: BeverageDao): BeverageRepository {
        return BeverageRepositoryImpl(beverageDao)
    }

    @Provides
    @Singleton
    fun provideHydratePresetRepository(hydratePresetDao: HydratePresetDao): HydratePresetRepository {
        return HydratePresetRepositoryImpl(hydratePresetDao)
    }

    @Provides
    @Singleton
    fun provideHydratedRecordRepository(
        hydratedRecordDao: HydratedRecordDao
    ): HydratedRecordRepository {
        return HydratedRecordRepositoryImpl(hydratedRecordDao)
    }
}