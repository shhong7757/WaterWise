package com.example.android.waterwise.ui.screen.beveragepreset

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.waterwise.data.HydratePreset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeveragePresetScreen(
    viewModel: BeveragePresetViewModel = hiltViewModel(), popBackStack: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(text = uiState.title)
        }, navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "설정 화면으로 이동")
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { /*TODO*/ }, modifier = Modifier.navigationBarsPadding()) {
            Icon(Icons.Filled.Add, "프리셋 추가")
        }
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            uiState.presets.map { preset ->
                PresetListItem(preset)
                Divider()
            }
        }
    }
}

@Composable
fun PresetListItem(preset: HydratePreset) {
    ListItem(
        headlineText = { Text(text = preset.nickname) },
        trailingContent = { Text(text = preset.amount.toString()) },
    )
}