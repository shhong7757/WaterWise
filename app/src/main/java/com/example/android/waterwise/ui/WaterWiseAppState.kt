package com.example.android.waterwise.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.android.waterwise.data.datastore.UserPreferencesRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberWaterWiseAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    userPreferencesRepository: UserPreferencesRepositoryImpl
): WaterWiseAppState {
    return remember {
        WaterWiseAppState(coroutineScope, userPreferencesRepository)
    }
}

@Stable
class WaterWiseAppState(
    coroutineScope: CoroutineScope,
    userPreferencesRepository: UserPreferencesRepositoryImpl
) {
    val hasShowOngoingScreenBefore =
        userPreferencesRepository.getHasShowOngoingScreenBefore()
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(),
                initialValue = true
            )
}