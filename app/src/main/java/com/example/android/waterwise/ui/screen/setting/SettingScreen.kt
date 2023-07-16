package com.example.android.waterwise.ui.screen.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    val inputGoalHydrationAmount = remember { mutableStateOf(0) }

    Column() {
        Text(text = "목표 수분 섭취량 : " + uiState.goalHydrationAmount.toString())
        TextField(
            value = inputGoalHydrationAmount.value.toString(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = {
                if (it == "") inputGoalHydrationAmount.value = 0
                else if (it.toIntOrNull() != null) inputGoalHydrationAmount.value = it.toInt()
            },
        )
        Button(
            onClick = {
                viewModel.setGoalHydrationAmount(inputGoalHydrationAmount.value)
                inputGoalHydrationAmount.value = 0
            },
            enabled = inputGoalHydrationAmount.value != 0
        ) {
            Text(text = "적용")
        }
    }
}