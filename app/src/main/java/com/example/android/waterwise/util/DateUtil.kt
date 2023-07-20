package com.example.android.waterwise.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun convertToLocalDateTimeToDate(localDateTime: LocalDateTime): Date {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}

fun getStartOfDay(date: LocalDateTime): LocalDateTime {
    return date.toLocalDate().atStartOfDay()
}

fun getEndOfDay(date: LocalDateTime): LocalDateTime {
    return date.toLocalDate().plusDays(1).atStartOfDay().minusNanos(1)
}

fun formatDateUsingSimpleDateFormat(date: Date, pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}