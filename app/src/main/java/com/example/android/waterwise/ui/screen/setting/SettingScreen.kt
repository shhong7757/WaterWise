package com.example.android.waterwise.ui.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
    navigateToBeveragePresetManagement: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column {
        ListItem(modifier = Modifier.clickable { navigateToProfile() },
            headlineText = { Text(text = "프로필") },
            supportingText = {
                Text(text = "목표 수분 섭취량 : " + uiState.goalHydrationAmount.toString())
            })
        Divider()
        ListItem(modifier = Modifier.clickable { navigateToBeveragePresetManagement() },
            headlineText = { Text(text = "음료 프리셋") })
        Divider()
    }
}