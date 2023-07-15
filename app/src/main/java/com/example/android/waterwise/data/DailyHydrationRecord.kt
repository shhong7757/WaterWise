package com.example.android.waterwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_hydration_record")
data class DailyHydrationRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val amount: Int,
    val beverage: String,
    val date: String, // YYYY/MM/DD 형식으로 저장,
)

