package com.solodev.ideahub.ui.screen.goalScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Date

class DayPlanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPlanUiState())
    private  val _dayPlanDialogUiState = MutableStateFlow(DayPlanItemUiState())
    val uiState: StateFlow<DayPlanUiState> = _uiState.asStateFlow()
    val dayPlanDialogUIState: StateFlow<DayPlanItemUiState> = _dayPlanDialogUiState.asStateFlow()

    fun addDayPlanItem(item: DayPlanItemUiState) {
        _uiState.value = _uiState.value.copy(dayPlans = _uiState.value.dayPlans + item)
    }

    fun toggleCompletion(index: Int) {
        val updatedItems = _uiState.value.dayPlans.toMutableList()
        updatedItems[index] = updatedItems[index].copy(isCompleted = !updatedItems[index].isCompleted)
        _uiState.value = _uiState.value.copy(dayPlans = updatedItems)
    }

    fun updateTitle(newTitle: String) {
        _dayPlanDialogUiState.value = _dayPlanDialogUiState.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _dayPlanDialogUiState.value = _dayPlanDialogUiState.value.copy(description = newDescription)
    }
}

data class DayPlanUiState(
    val dayPlans: List<DayPlanItemUiState> = emptyList(),
    val hasError: Boolean = false,
)

data class DayPlanItemUiState(
    val title: String? = null,
    val description: String = "",
    val creationDate: Date? = null,
    val deadline: Date? = null,
    val priority: Priority = Priority.LOW,
    val tags: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0
)
enum class Priority {
    HIGH, MEDIUM, LOW
}
