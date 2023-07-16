package com.example.android.waterwise.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android.waterwise.ui.screen.main.MainScreen
import com.example.android.waterwise.ui.screen.setting.SettingScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen() }
        composable("setting") { SettingScreen() }
    }
}