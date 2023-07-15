package com.example.android.waterwise.data.room

import androidx.room.TypeConverter
import com.example.android.waterwise.model.Beverage

class BeverageTypeConverters {
    @TypeConverter
    fun fromBeverage(value: Beverage): String {
        return value.name
    }

    @TypeConverter
    fun toBeverage(value: String): Beverage {
        return Beverage.valueOf(value)
    }
}