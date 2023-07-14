package com.example.android.waterwise.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.waterwise.ui.AppViewModelProvider
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val uiState = viewModel.uiState.collectAsState().value

    Column() {
        Today()
        CurrentHydrationStatus(uiState)
        HydrateButton(onHydrate = {
            viewModel.insertDailyHydrationRecord()
        })
    }
}

@Composable
fun Today() {
    Text(text = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
}

@Composable
fun CurrentHydrationStatus(uiState: HomeUiState) {
    Text(text = "오늘 수분 섭취량 : " + uiState.currentAmountOfHydration.toString())
}

@Composable
fun HydrateButton(onHydrate: () -> Unit) {
    Button(onClick = onHydrate) {
        Text(text = "수분 보충 + 30")
    }
}