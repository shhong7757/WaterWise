package com.example.android.waterwise.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.waterwise.ui.components.HydrateForm
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    Column() {
        Today()
        CurrentHydrationStatus(uiState)
        HydrateButton(onHydrate = {
            if (uiState.bottomSheetVisibility) {
                viewModel.hideBottomSheet()
            } else {
                viewModel.showBottomSheet()
            }
        })
        if (uiState.bottomSheetVisibility) {
            HydrateForm(beverageOptions = uiState.beverageOptions,
                hydratePresetOptions = uiState.hydratePresetOptions,
                onHydrateRequest = { hydrateAmount, beverageOption ->
                    viewModel.insertDailyHydrationRecord(hydrateAmount, beverageOption)
                })
        }
    }
}

@Composable
fun Today() {
    Text(text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
}

@Composable
fun CurrentHydrationStatus(uiState: MainUiState) {
    Column() {
        Text(text = "오늘 수분 섭취량 : " + uiState.currentAmountOfHydration.toString())
        Text(text = "목표 수분 섭취량 : " + uiState.goalHydrationAmount.toString())
    }
}

@Composable
fun HydrateButton(onHydrate: () -> Unit) {
    Button(onClick = onHydrate) {
        Text(text = "수분 보충")
    }
}