package com.example.android.waterwise.data.goal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "goal", indices = [Index(value = ["date"], unique = true)])
data class Goal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "goal_id") val id: Long = 0,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "value") val value: Int,
)
