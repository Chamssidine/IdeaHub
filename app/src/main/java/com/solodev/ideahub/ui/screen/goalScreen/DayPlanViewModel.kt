package com.solodev.ideahub.ui.screen.goalScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.util.Date

class DayPlanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPlanUiState())
    val uiState: StateFlow<DayPlanUiState> = _uiState

    fun addDayPlanItem(item: DayPlanItemUiState) {
        _uiState.value = _uiState.value.copy(dayPlans = _uiState.value.dayPlans + item)
    }

    fun toggleCompletion(index: Int) {
        val updatedItems = _uiState.value.dayPlans.toMutableList()
        updatedItems[index] = updatedItems[index].copy(isCompleted = !updatedItems[index].isCompleted)
        _uiState.value = _uiState.value.copy(dayPlans = updatedItems)
    }
}

data class DayPlanUiState(
    val dayPlans: List<DayPlanItemUiState> = emptyList(),
    val hasError: Boolean = false,
)

data class DayPlanItemUiState(
    val title: String,
    val description: String = "",
    val creationDate: Date,
    val deadline: Date,
    val priority: Priority,
    val tags: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0
)
enum class Priority {
    HIGH, MEDIUM, LOW
}
