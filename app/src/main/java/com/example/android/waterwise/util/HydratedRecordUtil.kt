package com.example.android.waterwise.util

import com.example.android.waterwise.data.hydratedrecord.HydratedRecord

fun sumOfHydratedAmount(hydratedRecords: List<HydratedRecord>): Int {
    return hydratedRecords.map { it.amount }.fold(0) { acc, curr ->
        acc + curr
    }
}