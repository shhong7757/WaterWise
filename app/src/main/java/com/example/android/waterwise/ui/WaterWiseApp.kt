package com.example.android.waterwise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.ui.WaterWiseAppState
import com.example.android.waterwise.ui.rememberWaterWiseAppState
import com.example.android.waterwise.ui.screen.HomeScreen
import com.example.android.waterwise.ui.screen.onboarding.OnBoardingScreen

@Composable
fun WaterWiseApp(
    userPreferencesRepository: DataStoreUserPreferencesRepository,
    appState: WaterWiseAppState = rememberWaterWiseAppState(
        userPreferencesRepository = userPreferencesRepository
    ),
) {
    val hasShowOngoingScreenBefore = appState.hasShowOngoingScreenBefore.collectAsState()
    val navController = rememberNavController()

    NavigationGraph(
        navController = navController,
        startDestination = if (hasShowOngoingScreenBefore.value) "home" else "onboarding"
    )
}

@Composable
fun NavigationGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") { HomeScreen() }
        composable("onboarding") { OnBoardingScreen() }
    }
}