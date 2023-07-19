package com.example.android.waterwise.ui.screen.beveragepresetmanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.waterwise.data.Beverage
import com.example.android.waterwise.ui.screen.profile.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeveragePresetManagementScreen(
    viewModel: BeveragePresetManagementViewModel = hiltViewModel(),
    navigateToBeveragePreset: (beverageId: Int) -> Unit,
    popBackStack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(topBar = {
        MediumTopAppBar(title = {
            Text(text = "음료 프리셋", maxLines = 1)
        }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "설정 화면으로 이동")
            }
        })
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            uiState.beverages.map { beverage ->
                BeverageListItem(beverage, navigateToBeveragePreset)
                Divider()
            }
        }
    }
}

@Composable
fun BeverageListItem(beverage: Beverage, navigateToBeveragePreset: (beverageId: Int) -> Unit) {
    ListItem(modifier = Modifier.clickable { navigateToBeveragePreset(beverage.id) },
        headlineText = { Text(text = beverage.value) })
}
