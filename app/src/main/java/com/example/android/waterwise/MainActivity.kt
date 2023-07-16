package com.example.android.waterwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.android.waterwise.data.UserPreferencesRepository
import com.example.android.waterwise.data.datastore.UserPreferencesRepositoryImpl
import com.example.android.waterwise.ui.theme.WaterWiseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterWiseTheme {
                WaterWiseApp(userPreferencesRepository)
            }
        }
    }
}