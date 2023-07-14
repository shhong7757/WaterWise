package com.example.android.waterwise.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android.waterwise.WaterWiseApplication
import com.example.android.waterwise.ui.screen.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                this.createSavedStateHandle(),
                waterWiseApplication().container.dailyHydrationRecordRepository
            )
        }
    }
}

fun CreationExtras.waterWiseApplication(): WaterWiseApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WaterWiseApplication)