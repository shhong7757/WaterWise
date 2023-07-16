package com.example.android.waterwise.ui.screen.onboarding.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun WelcomePage(next: () -> Unit) {
    Column() {
        Text(text = "Welcome")
        Button(onClick = { next() }) {
            Text(text = "다음")
        }
    }
}