package com.example.android.waterwise.data.hydratedrecord

import androidx.room.*
import com.example.android.waterwise.data.beverage.Beverage

@Entity(tableName = "hydrated_record")
data class HydratedRecord(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "hydrated_record_id") val id: Long = 0,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "beverage_id") val beverageId: Long,
    @ColumnInfo(name = "date") val date: String, // YYYY/MM/DD 형식으로 저장,
)

data class HydratedRecordAndBeverage(
    @Embedded val hydratedRecord: HydratedRecord, @Relation(
        parentColumn = "hydrated_record_id",
        entityColumn = "beverage_id",
    ) val beverage: Beverage
)
