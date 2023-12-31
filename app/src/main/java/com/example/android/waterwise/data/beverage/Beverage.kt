package com.example.android.waterwise.data.beverage

import androidx.room.*
import com.example.android.waterwise.data.hydratepreset.HydratePreset

@Entity(tableName = "beverage")
data class Beverage(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "beverage_id") val id: Long = 0,
    @ColumnInfo(name = "color") val color: Long,
    @ColumnInfo(name = "value") val value: String,
)

data class BeverageWithHydratePresets(
    @Embedded val beverage: Beverage, @Relation(
        parentColumn = "beverage_id", entityColumn = "beverage_id"
    ) val hydratePresets: List<HydratePreset>
)
