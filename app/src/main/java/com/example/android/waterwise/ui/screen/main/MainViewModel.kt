package com.example.android.waterwise.ui.screen.main

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.beverage.BeverageRepository
import com.example.android.waterwise.data.hydratedrecord.HydratedRecord
import com.example.android.waterwise.data.hydratedrecord.impl.RoomHydratedRecordRepository
import com.example.android.waterwise.data.preferences.impl.DataStoreUserPreferencesRepository
import com.example.android.waterwise.util.convertToLocalDateTimeToDate
import com.example.android.waterwise.util.getEndOfDay
import com.example.android.waterwise.util.getStartOfDay
import com.example.android.waterwise.util.sumOfHydratedAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val beverageRepository: BeverageRepository,
    private val hydratedRecordRepository: RoomHydratedRecordRepository,
    private val userPreferencesRepository: DataStoreUserPreferencesRepository
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
        fetchGoalHydrationAmount()
        fetchBeverageList()
        fetchBeveragePresetList()
        fetchHydratedRecord()
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

    private fun fetchGoalHydrationAmount() {
        viewModelScope.launch {
            userPreferencesRepository.getUserProfile().collect {
                _uiState.value = _uiState.value.copy(goalHydrationAmount = it.goalHydrationAmount)
            }
        }
    }

    private fun fetchHydratedRecord() {
        viewModelScope.launch {
            val start = convertToLocalDateTimeToDate(getStartOfDay(LocalDate.now()))
            val end = convertToLocalDateTimeToDate(getEndOfDay(LocalDate.now()))

            hydratedRecordRepository.getAllHydratedRecordByDate(
                start, end
            ).map {
                sumOfHydratedAmount(it)
            }.collect { currentAmountOfHydration ->
                _uiState.value =
                    _uiState.value.copy(currentAmountOfHydration = currentAmountOfHydration)
            }
        }
    }

    fun insertDailyHydrationRecord(amount: Int, beverageOption: BeverageOption) {
        viewModelScope.launch {
            val date: Date = convertToLocalDateTimeToDate(LocalDateTime.now())
            hydratedRecordRepository.insertHydratedRecord(
                HydratedRecord(
                    amount = amount,
                    beverageId = beverageOption.beverageId,
                    date = date,
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
