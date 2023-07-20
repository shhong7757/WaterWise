package com.example.android.waterwise.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

fun convertToLocalDateTimeToDate(localDateTime: LocalDateTime): Date {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}

fun getStartOfDay(date: LocalDate): LocalDateTime {
    return date.atStartOfDay()
}

fun getEndOfDay(date: LocalDate): LocalDateTime {
    return date.atTime(LocalTime.MAX)
}