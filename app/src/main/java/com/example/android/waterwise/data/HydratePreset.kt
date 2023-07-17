package com.example.android.waterwise.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hydrate_preset", foreignKeys = [ForeignKey(
        entity = Beverage::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("beverageId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class HydratePreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, val beverageId: Int, val hydrationAmount: Int,
)
