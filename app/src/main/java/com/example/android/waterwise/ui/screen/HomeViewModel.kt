package com.example.android.waterwise.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.DailyHydrationRecord
import com.example.android.waterwise.data.room.DailyHydrationRecordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val dailyHydrationRecordRepository: DailyHydrationRecordRepository

) : ViewModel() {
    private val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

    val uiState: StateFlow<HomeUiState> =
        dailyHydrationRecordRepository.getAllHydrationAmountStream(date).map {
            val amountOfHydration =
                it.map { dailyHydrationRecord -> dailyHydrationRecord.hydrationAmount }
                    .fold(
                        0
                    ) { totalAmountOfHydration,
                        hydrationAmount ->
                        totalAmountOfHydration + hydrationAmount
                    }
            HomeUiState(currentAmountOfHydration = amountOfHydration)
        }.stateIn(
            scope = viewModelScope,
            initialValue = HomeUiState(currentAmountOfHydration = 0),
            started = SharingStarted.WhileSubscribed()
        )

    fun insertDailyHydrationRecord() {
        viewModelScope.launch {
            val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            val hydrationAmount = 30
            dailyHydrationRecordRepository.insertDailyHydrationRecord(
                DailyHydrationRecord(
                    date = today,
                    hydrationAmount = hydrationAmount
                )
            )
        }


    }
}

data class HomeUiState(
    val currentAmountOfHydration: Int
)