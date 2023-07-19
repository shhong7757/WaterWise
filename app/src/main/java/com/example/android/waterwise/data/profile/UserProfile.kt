package com.example.android.waterwise.data.profile

import com.example.android.waterwise.model.Sex

data class UserProfile(
    val goalHydrationAmount: Int = 20000,
    val height: Int?,
    val sex: Sex?,
    val width: Int?
)
