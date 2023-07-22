package com.example.android.waterwise.ui.screen.beveragepreset

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.waterwise.data.beverage.BeverageRepository
import com.example.android.waterwise.data.hydratepreset.HydratePreset
import com.example.android.waterwise.data.hydratepreset.HydratePresetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeveragePresetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val beverageRepository: BeverageRepository,
    private val hydratedPresetRepository: HydratePresetRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        BeveragePresetUiState(beverageId = savedStateHandle.get<Long>("id"))
    )
    val uiState: StateFlow<BeveragePresetUiState> = _uiState

    init {
        fetchHydratePresetsByBeverageId(savedStateHandle.get<Long>("id"))
    }

    private fun fetchHydratePresetsByBeverageId(id: Long?) {
        viewModelScope.launch {
            if (id != null) {
                beverageRepository.getBeverageWithHydratePresetsById(id).collect {
                    _uiState.value = _uiState.value.copy(
                        title = it.beverage.value, presets = it.hydratePresets
                    )
                }
            }

        }
    }

    fun insertHydratedPreset(nickname: String, amount: Int) {
        viewModelScope.launch {
            val beverageId = uiState.value.beverageId
            if (beverageId != null) {
                hydratedPresetRepository.insertHydratePreset(
                    HydratePreset(
                        amount = amount, beverageId = beverageId, nickname = nickname
                    )
                )
            }
        }
    }
}


data class BeveragePresetUiState(
    val beverageId: Long? = null,
    val title: String = "",
    val presets: List<HydratePreset> = listOf(),
)