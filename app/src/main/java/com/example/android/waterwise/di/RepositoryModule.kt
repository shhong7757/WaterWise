package com.example.android.waterwise.di

import com.example.android.waterwise.data.*
import com.example.android.waterwise.data.beverage.BeverageDao
import com.example.android.waterwise.data.beverage.BeverageRepository
import com.example.android.waterwise.data.dateRecord.DateRecordDao
import com.example.android.waterwise.data.dateRecord.DateRecordRepository
import com.example.android.waterwise.data.dateRecord.impl.RoomDateRecordRepository
import com.example.android.waterwise.data.goal.GoalDao
import com.example.android.waterwise.data.goal.GoalRepository
import com.example.android.waterwise.data.goal.impl.RoomGoalRepository
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordDao
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordRepository
import com.example.android.waterwise.data.hydratedrecord.impl.RoomHydratedRecordRepository
import com.example.android.waterwise.data.hydratepreset.HydratePresetDao
import com.example.android.waterwise.data.hydratepreset.HydratePresetRepository
import com.example.android.waterwise.data.hydratepreset.impl.RoomHydratePresetRepository
import com.example.android.waterwise.data.room.BeverageRepositoryImpl
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
    fun provideDateRecordRepository(dateRecordDao: DateRecordDao): DateRecordRepository {
        return RoomDateRecordRepository(dateRecordDao)
    }

    @Provides
    @Singleton
    fun provideGoalRepository(dateRecordDao: DateRecordDao, goalDao: GoalDao): GoalRepository {
        return RoomGoalRepository(dateRecordDao, goalDao)
    }

    @Provides
    @Singleton
    fun provideHydratePresetRepository(hydratePresetDao: HydratePresetDao): HydratePresetRepository {
        return RoomHydratePresetRepository(hydratePresetDao)
    }

    @Provides
    @Singleton
    fun provideHydratedRecordRepository(
        dateRecordDao: DateRecordDao,
        goalDao: GoalDao,
        hydratedRecordDao: HydratedRecordDao,
    ): HydratedRecordRepository {
        return RoomHydratedRecordRepository(dateRecordDao, goalDao, hydratedRecordDao)
    }
}