package com.example.android.waterwise

import android.app.Application
import com.example.android.waterwise.data.AppContainer
import com.example.android.waterwise.data.AppDataContainer

class WaterWiseApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}