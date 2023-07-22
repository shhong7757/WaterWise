package com.example.android.waterwise.ui.screen.main

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.beverage.BeverageRepository
import com.example.android.waterwise.data.dateRecord.DateRecordRepository
import com.example.android.waterwise.data.goal.impl.RoomGoalRepository
import com.example.android.waterwise.data.hydratedrecord.impl.RoomHydratedRecordRepository
import com.example.android.waterwise.util.convertToLocalDateTimeToDate
import com.example.android.waterwise.util.getStartOfDay
import com.example.android.waterwise.util.sumOfHydratedAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dateRecordRepository: DateRecordRepository,
    private val beverageRepository: BeverageRepository,
    private val goalRepository: RoomGoalRepository,
    private val hydratedRecordRepository: RoomHydratedRecordRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MainUiState(
            beverageOptions = listOf(),
            bottomSheetVisibility = false,
            hydratePresetOptions = listOf(),
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        fetchLastGoal()
        fetchBeverageList()
        fetchBeveragePresetList()
        fetchHydratedRecord()
    }

    private fun fetchLastGoal() {
        viewModelScope.launch {
            goalRepository.getLastGoalFlow().collect {
                _uiState.value =
                    _uiState.value.copy(goalHydrationAmount = it?.value ?: 0)
            }
        }
    }

    private fun fetchBeverageList() {
        viewModelScope.launch {
            beverageRepository.getAllBeverageStream().collect { beverages ->
                _uiState.value = _uiState.value.copy(beverageOptions = beverages.map { beverage ->
                    BeverageOption(
                        beverageId = beverage.id,
                        color = Color(beverage.color),
                        label = beverage.value
                    )
                })
            }
        }
    }

    private fun fetchBeveragePresetList() {
        viewModelScope.launch {
            beverageRepository.getAllBeverageWithHydratePresetsStream()
                .collect { beverageWithHydratePresets ->
                    beverageWithHydratePresets.map { beverageWithHydratePreset ->
                        val beverage = beverageWithHydratePreset.beverage
                        beverageWithHydratePreset.hydratePresets.map { hydratePreset ->
                            HydratePresetOption(
                                beverageOption = BeverageOption(
                                    beverageId = beverage.id,
                                    color = Color(beverage.color),
                                    label = hydratePreset.nickname
                                ), hydrationAmount = hydratePreset.amount
                            )
                        }
                    }.fold(listOf<HydratePresetOption>()) { acc, curr ->
                        acc.plus(curr)
                    }.let {
                        _uiState.value = _uiState.value.copy(hydratePresetOptions = it)
                    }
                }
        }
    }

    private fun fetchHydratedRecord() {
        viewModelScope.launch {
            val now = LocalDateTime.now()
            val date = convertToLocalDateTimeToDate(getStartOfDay(now))
            dateRecordRepository.getDateRecordAndGoalWithHydratedRecords(date).collect {
                if (it != null) {
                    _uiState.value = _uiState.value.copy(
                        currentAmountOfHydration = sumOfHydratedAmount(it.hydratedRecords),
                    )
                }
            }
        }
    }

    fun insertDailyHydrationRecord(amount: Int, beverageOption: BeverageOption) {
        viewModelScope.launch {
            hydratedRecordRepository.insertHydratedRecord(
                amount = amount, beverage_id = beverageOption.beverageId
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

data class MainUiState(
    val beverageOptions: List<BeverageOption>,
    val bottomSheetVisibility: Boolean,
    val currentAmountOfHydration: Int = 0,
    val goalHydrationAmount: Int = 0,
    val hydratePresetOptions: List<HydratePresetOption>,
)

data class BeverageOption(
    val beverageId: Long,
    val color: Color,
    val label: String,
)

data class HydratePresetOption(
    val beverageOption: BeverageOption,
    val hydrationAmount: Int,
)
