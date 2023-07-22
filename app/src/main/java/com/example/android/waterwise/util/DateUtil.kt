package com.example.android.waterwise.util

import com.example.android.waterwise.model.Day
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun convertToLocalDateTimeToDate(localDateTime: LocalDateTime): Date {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
}

fun convertLongToLocalDateTime(timestamp: Long): LocalDateTime {
    val instant: Instant = Instant.ofEpochMilli(timestamp)
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun convertLocalDateTimeToLong(localDateTime: LocalDateTime): Long {
    val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
    return instant.toEpochMilli()
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

fun getDayFromDate(date: LocalDateTime): Day {
    return getDayFromDayOfWeek(date.dayOfWeek)
}

fun getDayFromDayOfWeek(dayOfWeek: DayOfWeek): Day {
    return when (dayOfWeek) {
        DayOfWeek.SATURDAY -> Day.SAT
        DayOfWeek.MONDAY -> Day.MON
        DayOfWeek.TUESDAY -> Day.TUE
        DayOfWeek.WEDNESDAY -> Day.WED
        DayOfWeek.THURSDAY -> Day.THU
        DayOfWeek.FRIDAY -> Day.FRI
        DayOfWeek.SUNDAY -> Day.SUN
    }
}

fun getWeekRangeFromDate(date: LocalDateTime): List<LocalDateTime> {
    val startDateOfWeek = when (getDayFromDate(date)) {
        Day.MON -> 1
        Day.TUE -> 2
        Day.WED -> 3
        Day.THU -> 4
        Day.FRI -> 5
        Day.SAT -> 6
        Day.SUN -> 0
    }.let {
        date.minusDays(it.toLong())
    }

    return listOf(
        startDateOfWeek,
        startDateOfWeek.plusDays(1),
        startDateOfWeek.plusDays(2),
        startDateOfWeek.plusDays(3),
        startDateOfWeek.plusDays(4),
        startDateOfWeek.plusDays(5),
        startDateOfWeek.plusDays(6)
    )
}