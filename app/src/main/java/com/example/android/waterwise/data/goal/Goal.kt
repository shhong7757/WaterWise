package com.example.android.waterwise.data.goal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "goal_id") val id: Long = 0,
    @ColumnInfo(name = "value") val value: Int
)
