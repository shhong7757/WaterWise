package com.example.android.waterwise.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android.waterwise.navigation.HomeNavGraph

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            HomeNavGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }
    val screens = listOf("main", "setting")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigationBarDestination = screens.any { it == currentDestination?.route }

    if (navigationBarDestination) {
        NavigationBar {
            screens.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        when (item) {
                            "main" -> navController.navigate("main")
                            "setting" -> navController.navigate("setting")
                        }
                    }
                )
            }
        }
    }
}