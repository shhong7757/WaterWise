package com.example.android.waterwise.data.room

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun toLong(color: Color): Long {
        return color.value.toLong()
    }

    @TypeConverter
    fun toColor(value: Long): Color {
        return Color(value)
    }
}