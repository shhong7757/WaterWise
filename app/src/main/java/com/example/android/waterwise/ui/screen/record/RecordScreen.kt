package com.example.android.waterwise.ui.screen.record

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.waterwise.model.Day
import java.time.LocalDateTime

@Composable
fun RecordScreen(viewModel: RecordViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    val days = enumValues<Day>()
    val tableColumnData: List<Pair<Int, List<LocalDateTime>>> = listOf(Pair(0, uiState.weekRange))

    Column() {
        LazyColumn() {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    days.map {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it.name,
                        )
                    }
                }
            }

            items(tableColumnData) {
                Row() {
                    tableColumnData.map {
                        it.second.map { localDateTime ->
                            Column(modifier = Modifier
                                .weight(1f)
                                .clickable(localDateTime.dayOfMonth <= LocalDateTime.now().dayOfMonth) {
                                    viewModel.updateSelectedDate(
                                        localDateTime
                                    )
                                }) {
                                Text(
                                    text = localDateTime.dayOfMonth.toString(),
                                    fontWeight = if (localDateTime.dayOfMonth == uiState.selectedDate.dayOfMonth) FontWeight.Bold else FontWeight.Normal
                                )
                            }

                        }
                    }
                }
            }
        }



        Row() {
            Text(text = "수분 섭취량")
            Text(text = uiState.totalHydrationAmount.toString() + " / " + uiState.goalHydrationAmount.toString())
        }
        Column() {
            uiState.timeline.map {
                Row() {
                    Text(text = it.date.toString())
                    Text(text = it.value)
                    Text(text = it.amount.toString())
                }
            }
        }
    }
}