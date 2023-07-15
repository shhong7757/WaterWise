package com.example.android.waterwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.waterwise.model.Beverage

@Entity(tableName = "daily_hydration_record")
data class DailyHydrationRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val amount: Int,
    val beverage: Beverage,
    val date: String, // YYYY/MM/DD 형식으로 저장,
)

