package com.example.android.waterwise.ui.screen.record

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.dateRecord.DateRecordRepository
import com.example.android.waterwise.data.goal.impl.RoomGoalRepository
import com.example.android.waterwise.util.convertToLocalDateTimeToDate
import com.example.android.waterwise.util.formatDateUsingSimpleDateFormat
import com.example.android.waterwise.util.getStartOfDay
import com.example.android.waterwise.util.getWeekRangeFromDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val dateRecordRepository: DateRecordRepository,
    private val goalRepository: RoomGoalRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        RecordUiState()
    )
    val uiState: StateFlow<RecordUiState> = _uiState

    init {
        updateSelectedDate(uiState.value.selectedDate)
        updateWeekRange(uiState.value.selectedDate)
    }

    fun updateSelectedDate(date: LocalDateTime) {
        viewModelScope.launch {
            val start = convertToLocalDateTimeToDate(getStartOfDay(date))
            dateRecordRepository.getDateRecordAndGoalWithHydratedRecordAndBeverageList(start)
                .collect { dateRecordAndGoalWithHydratedRecordAndBeverageList ->
                    if (dateRecordAndGoalWithHydratedRecordAndBeverageList == null) {
                        _uiState.value = _uiState.value.copy(
                            selectedDate = date,
                            goalHydrationAmount = goalRepository.getLastGoal().value,
                            timeline = listOf(),
                            totalHydrationAmount = 0,
                        )
                    } else {
                        dateRecordAndGoalWithHydratedRecordAndBeverageList.hydratedRecordAndBeverageList.map { hydratedRecordAndBeverage ->
                            val hydratedRecord = hydratedRecordAndBeverage.hydratedRecord
                            val beverage = hydratedRecordAndBeverage.beverage
                            val formattedDate =
                                formatDateUsingSimpleDateFormat(hydratedRecord.date, "HH:mm:ss")

                            HydratedRecordTimeline(
                                date = formattedDate,
                                value = beverage.value,
                                amount = hydratedRecord.amount,
                                id = hydratedRecord.id,
                                color = Color(beverage.color)
                            )
                        }.let {
                            _uiState.value = _uiState.value.copy(selectedDate = date,
                                goalHydrationAmount = dateRecordAndGoalWithHydratedRecordAndBeverageList.goal.value,
                                timeline = it,
                                totalHydrationAmount = it.fold(0) { acc, curr ->
                                    acc + curr.amount
                                })
                        }
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