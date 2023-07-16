package com.example.android.waterwise.ui.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.waterwise.data.UserProfile
import com.example.android.waterwise.ui.screen.onboarding.page.CustomizeUserProfilePage
import com.example.android.waterwise.ui.screen.onboarding.page.WelcomePage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(viewModel: OnBoardingViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val pageCount = 3
    val pagerState = rememberPagerState()

    Column() {
        HorizontalPager(
            state = pagerState, pageCount = pageCount, userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> WelcomePage(next = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                })
                1 -> CustomizeUserProfilePage(next = { goalHydrationAmount, height, sex, width ->
                    viewModel.setUserProfile(
                        UserProfile(
                            goalHydrationAmount, height, sex, width
                        )
                    )

                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                })
                2 -> {
                    Row() {
                        Button(onClick = { viewModel.setHasShowOngoingScreenBefore(true) }) {
                            Text(text = "시작하기")
                        }
                    }
                }
            }
        }
        Row() {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(20.dp)
                )
            }
        }
    }
}