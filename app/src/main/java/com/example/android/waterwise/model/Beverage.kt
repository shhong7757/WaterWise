package com.example.android.waterwise.model

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.android.waterwise.R

enum class Beverage {
    Water {
        override fun getColor(): Color {
            return Color(0xFFFF0000)
        }

        override fun getLabel(context: Context): String {
            return context.getString(R.string.water)
        }
    },
    Coffee {
        override fun getColor(): Color {
            return Color(0xFF00FF00)
        }

        override fun getLabel(context: Context): String {
            return context.getString(R.string.coffee)
        }
    };

    abstract fun getColor(): Color
    abstract fun getLabel(context: Context): String
}
