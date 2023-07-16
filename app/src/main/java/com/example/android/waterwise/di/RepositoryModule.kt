package com.example.android.waterwise.di

import com.example.android.waterwise.data.DailyHydrationRecordDao
import com.example.android.waterwise.data.DailyHydrationRecordRepository
import com.example.android.waterwise.data.room.DailyHydrationRecordRepositoryImpl
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
    fun provideDailyHydrationRecordRepository(
        dailyHydrationRecordDao: DailyHydrationRecordDao
    ): DailyHydrationRecordRepository {
        return DailyHydrationRecordRepositoryImpl(dailyHydrationRecordDao)
    }
}