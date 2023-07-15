package com.example.android.waterwise.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.DailyHydrationRecord
import com.example.android.waterwise.data.room.DailyHydrationRecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val dailyHydrationRecordRepository: DailyHydrationRecordRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(bottomSheetVisibility = false))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        observeDailyHydrationRecord()
    }

    private fun observeDailyHydrationRecord() {
        viewModelScope.launch {
            val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            dailyHydrationRecordRepository.getAllHydrationAmountStream(
                date
            ).map {
                it.map { dailyHydrationRecord -> dailyHydrationRecord.amount }
                    .fold(0) { totalAmountOfHydration, hydrationAmount ->
                        totalAmountOfHydration + hydrationAmount
                    }
            }.collect { currentAmountOfHydration ->
                _uiState.value =
                    _uiState.value.copy(currentAmountOfHydration = currentAmountOfHydration)
            }
        }
    }

    fun insertDailyHydrationRecord(amount: Int, beverage: String) {
        viewModelScope.launch {
            val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            dailyHydrationRecordRepository.insertDailyHydrationRecord(
                DailyHydrationRecord(
                    amount = amount,
                    beverage = beverage,
                    date = today,
                )
            )

            _uiState.value = _uiState.value.copy(bottomSheetVisibility = false)
        }
    }

    fun hideBottomSheet() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(bottomSheetVisibility = false)
        }
    }

    fun showBottomSheet() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(bottomSheetVisibility = true)
        }
    }
}

data class HomeUiState(
    val bottomSheetVisibility: Boolean,
    val currentAmountOfHydration: Int = 0
)