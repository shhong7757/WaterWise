package com.example.android.waterwise.util

import com.example.android.waterwise.data.hydratedrecord.HydratedRecord

fun sumOfHydratedAmount(hydratedRecordList: List<HydratedRecord>): Int {
    return hydratedRecordList.map { it.amount }.fold(0) { acc, curr -> acc + curr }
}