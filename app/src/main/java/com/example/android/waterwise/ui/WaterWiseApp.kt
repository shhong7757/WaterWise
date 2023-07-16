package com.example.android.waterwise

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.waterwise.data.datastore.UserPreferencesRepositoryImpl
import com.example.android.waterwise.ui.WaterWiseAppState
import com.example.android.waterwise.ui.rememberWaterWiseAppState
import com.example.android.waterwise.ui.screen.home.HomeScreen
import com.example.android.waterwise.ui.screen.onboarding.OnBoardingScreen
import com.example.android.waterwise.ui.screen.profile.ProfileScreen

@Composable
fun WaterWiseApp(
    userPreferencesRepository: UserPreferencesRepositoryImpl,
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