package com.example.android.waterwise.ui.screen.record

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.hydratedrecord.impl.RoomHydratedRecordRepository
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val hydratedRecordRepository: RoomHydratedRecordRepository,
    private val userPreferencesRepository: DataStoreUserPreferencesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        RecordUiState()
    )
    val uiState: StateFlow<RecordUiState> = _uiState

    init {
        updateSelectedDate(uiState.value.selectedDate)
        updateWeekRange(uiState.value.selectedDate)
        updateGoalHydrationAmount()
    }

    private fun updateGoalHydrationAmount() {
        viewModelScope.launch {
            userPreferencesRepository.getUserProfile().collect {
                _uiState.value = _uiState.value.copy(goalHydrationAmount = it.goalHydrationAmount)
            }
        }
    }

    fun updateSelectedDate(date: LocalDateTime) {
        viewModelScope.launch {
            val start = convertToLocalDateTimeToDate(getStartOfDay(date))
            val end = convertToLocalDateTimeToDate(getEndOfDay(date))

            hydratedRecordRepository.getAllHydratedRecordAndBeverageByDate(
                start, end
            ).collect { arrayOfHydratedRecordAndBeverage ->
                arrayOfHydratedRecordAndBeverage.map { hydratedRecordAndBeverage ->
                    val hydratedRecord = hydratedRecordAndBeverage.hydratedRecord
                    val beverage = hydratedRecordAndBeverage.beverage
                    val formattedDate =
                        formatDateUsingSimpleDateFormat(hydratedRecord.date, "HH:mm:ss")

                    println(hydratedRecord.date)
                    HydratedRecordTimeline(
                        date = formattedDate,
                        value = beverage.value,
                        amount = hydratedRecord.amount,
                        id = hydratedRecord.id,
                        color = Color(beverage.color)
                    )
                }.let {
                    _uiState.value = _uiState.value.copy(selectedDate = date,
                        timeline = it,
                        totalHydrationAmount = it.fold(0) { acc, curr ->
                            acc + curr.amount
                        })
                }
            }
        }
    }

    fun updateWeekRange(date: LocalDateTime) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(weekRange = getWeekRangeFromDate(date))
        }
    }
}

data class RecordUiState(
    val goalHydrationAmount: Int = 0,
    val totalHydrationAmount: Int = 0,
    val timeline: List<HydratedRecordTimeline> = listOf(),
    val weekRange: List<LocalDateTime> = listOf(),
    val selectedDate: LocalDateTime = LocalDateTime.now(),
)

data class HydratedRecordTimeline(
    val date: String, val value: String, val amount: Int, val id: Long, val color: Color
)