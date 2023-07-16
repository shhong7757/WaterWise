package com.example.android.waterwise.ui.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnBoardingScreen(viewModel: OnBoardingViewModel = hiltViewModel()) {
    Column() {
        Text(text = "OnBoarding")
        Button(onClick = { viewModel.setHasShowOngoingScreenBefore(true) }) {
            Text(text = "홈으로")
        }
    }
}