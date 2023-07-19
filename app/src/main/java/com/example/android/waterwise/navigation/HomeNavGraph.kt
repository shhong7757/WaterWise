package com.example.android.waterwise.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android.waterwise.ui.screen.beveragepreset.BeveragePresetScreen
import com.example.android.waterwise.ui.screen.beveragepresetmanagement.BeveragePresetManagementScreen
import com.example.android.waterwise.ui.screen.main.MainScreen
import com.example.android.waterwise.ui.screen.profile.ProfileScreen
import com.example.android.waterwise.ui.screen.record.RecordScreen
import com.example.android.waterwise.ui.screen.setting.SettingScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen() }
        composable("setting") {
            SettingScreen(navigateToProfile = { navController.navigate("profile") },
                navigateToBeveragePresetManagement = { navController.navigate("beveragePresetManagement") })
        }
        composable("record") { RecordScreen() }

        composable("profile") { ProfileScreen(popBackStack = { navController.popBackStack() }) }

        composable(
            "beveragePreset/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            BeveragePresetScreen(popBackStack = { navController.popBackStack() })
        }
        composable("beveragePresetManagement") {
            BeveragePresetManagementScreen(navigateToBeveragePreset = { beverageId ->
                navController.navigate(
                    "beveragePreset/$beverageId"
                )
            }, popBackStack = { navController.popBackStack() })
        }
    }
}