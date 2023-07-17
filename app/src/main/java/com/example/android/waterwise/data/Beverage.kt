package com.example.android.waterwise.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "beverage")
data class Beverage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val value: String,
    val symbolColor: Long
)

data class BeverageWithHydratePresets(
    @Embedded val beverage: Beverage,
    @Relation(
        parentColumn = "id",
        entityColumn = "beverageId")
    val hydratePresets: List<HydratePreset>
)
