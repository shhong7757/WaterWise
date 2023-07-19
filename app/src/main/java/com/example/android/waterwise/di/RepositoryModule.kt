package com.example.android.waterwise.di

import com.example.android.waterwise.data.*
import com.example.android.waterwise.data.beverage.BeverageDao
import com.example.android.waterwise.data.beverage.BeverageRepository
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordRepository
import com.example.android.waterwise.data.hydratepreset.HydratePresetDao
import com.example.android.waterwise.data.hydratepreset.HydratePresetRepository
import com.example.android.waterwise.data.room.BeverageRepositoryImpl
import com.example.android.waterwise.data.hydratepreset.impl.RoomHydratePresetRepository
import com.example.android.waterwise.data.hydratedrecord.impl.RoomHydratedRecordRepository
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
        return RoomHydratePresetRepository(hydratePresetDao)
    }

    @Provides
    @Singleton
    fun provideHydratedRecordRepository(
        hydratedRecordDao: HydratedRecordDao
    ): HydratedRecordRepository {
        return RoomHydratedRecordRepository(hydratedRecordDao)
    }
}