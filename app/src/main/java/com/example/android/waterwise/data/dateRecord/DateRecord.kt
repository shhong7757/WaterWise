package com.example.android.waterwise.data.dateRecord

import androidx.room.*
import com.example.android.waterwise.data.goal.Goal
import com.example.android.waterwise.data.hydratedrecord.HydratedRecord
import com.example.android.waterwise.data.hydratedrecord.HydratedRecordAndBeverage
import java.util.*

@Entity(tableName = "date_record", indices = [Index(value = ["date"], unique = true)])
data class DateRecord(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "date_record_id") val id: Long = 0,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "goal_id") val goalId: Long
)

data class DateRecordWithHydratedRecords(
    @Embedded val dateRecord: DateRecord,
    @Relation(
        parentColumn = "date_record_id", entityColumn = "date_record_id"
    ) val hydratedRecords: List<HydratedRecord>
)

data class DateRecordAndGoalWithHydratedRecords(
    @Embedded val dateRecord: DateRecord,
    @Relation(parentColumn = "goal_id", entityColumn = "goal_id") val goal: Goal,
    @Relation(
        parentColumn = "date_record_id", entityColumn = "date_record_id"
    ) val hydratedRecords: List<HydratedRecord>
)

data class DateRecordAndGoalWithHydratedRecordAndBeverageList(
    @Embedded val dateRecord: DateRecord,
    @Relation(parentColumn = "goal_id", entityColumn = "goal_id") val goal: Goal,
    @Relation(
        entity = HydratedRecord::class,
        parentColumn = "date_record_id",
        entityColumn = "date_record_id",
    ) val hydratedRecordAndBeverageList: List<HydratedRecordAndBeverage>
)