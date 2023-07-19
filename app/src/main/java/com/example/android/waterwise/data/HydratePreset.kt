package com.example.android.waterwise.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hydrate_preset", foreignKeys = [ForeignKey(
        entity = Beverage::class,
        parentColumns = arrayOf("beverage_id"),
        childColumns = arrayOf("beverage_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class HydratePreset(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "hydrate_preset_id") val id: Int = 0,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "beverage_id") val beverageId: Int,
    @ColumnInfo(name = "nickname") val nickname: String,
)
