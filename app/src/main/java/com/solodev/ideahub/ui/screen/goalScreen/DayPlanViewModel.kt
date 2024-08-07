package com.solodev.ideahub.ui.screen.goalScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.solodev.ideahub.util.Tools.Companion.convertLongToTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class DayPlanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPlanUiState())
    private  val _dayPlanDialogUiState = MutableStateFlow(DayPlanItemUiState())
    val uiState: StateFlow<DayPlanUiState> = _uiState.asStateFlow()
    val dayPlanDialogUIState: StateFlow<DayPlanItemUiState> = _dayPlanDialogUiState.asStateFlow()

    fun createDayPlanItem():DayPlanItemUiState? {
        if (validateInputs()) {
            return dayPlanDialogUIState.value
        }
        else
            return null

    }
    fun addDayPlanItem(item: DayPlanItemUiState) {
        _uiState.value = _uiState.value.copy(dayPlans = _uiState.value.dayPlans + item)
    }

    fun toggleCompletion(index: Int) {
        val updatedItems = _uiState.value.dayPlans.toMutableList()
        updatedItems[index] = updatedItems[index].copy(isCompleted = !updatedItems[index].isCompleted)
        _uiState.value = _uiState.value.copy(dayPlans = updatedItems)
    }

    fun updateTitle(newTitle: String) {
        Log.d("DayPlanViewModel", "updateTitle called with newTitle: $newTitle")
        _dayPlanDialogUiState.value = _dayPlanDialogUiState.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _dayPlanDialogUiState.value = _dayPlanDialogUiState.value.copy(description = newDescription)
    }

    fun updatePriority(priority: Priority) {
                _dayPlanDialogUiState.update {
                    state -> state.copy(priority = priority)
                }
    }
    fun updateDeadline( deadline: Date) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(deadline)
        _dayPlanDialogUiState.update {
           state -> state.copy(deadline = formattedDate)
       }
    }
    fun updateProgress(progress: Int) {
        _dayPlanDialogUiState.update {
                state -> state.copy(progress = progress)
        }
    }

    fun onEditGoal(toEdit: DayPlanItemUiState) {
        _dayPlanDialogUiState.update {
                state -> state.copy(
                    title = toEdit.title,
                    description = toEdit.description,
                    deadline = toEdit.deadline,
                    priority =  toEdit.priority,
                    progress = toEdit.progress,

                )
        }

    }
    private fun validateInputs(): Boolean {
        val state = _dayPlanDialogUiState.value
        return when {
            state.title.isBlank() -> {
                _dayPlanDialogUiState.update {
                    state -> state.copy(hasError = true, errorMessage = "The title is empty") }
                Log.d("DayPlanViewModel", "Error: ${_dayPlanDialogUiState.value.errorMessage}")
                false
            }
            state.description.isBlank() -> {
                _dayPlanDialogUiState.update { state -> state.copy(hasError = true, errorMessage = "The description is empty") }
                Log.d("DayPlanViewModel", "Error: ${_dayPlanDialogUiState.value.errorMessage}")
                false
            }
            !isValidDate(state.deadline) -> {
                _dayPlanDialogUiState.update { state -> state.copy(hasError = true, errorMessage = "Invalid deadline date") }
                Log.d("DayPlanViewModel", "Error: ${_dayPlanDialogUiState.value.errorMessage}")
                false
            }
            else -> {
                _dayPlanDialogUiState.value = state.copy(hasError = false, errorMessage = null)
                true
            }
        }
    }
    private fun isValidDate(date: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            dateFormat.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}

data class DayPlanUiState(
    val dayPlans: List<DayPlanItemUiState> = emptyList(),
    val hasError: Boolean = false,
)

data class DayPlanItemUiState(
    val title: String = "",
    val description: String = "",
    val creationDate: Date = Date(),
    val deadline: String = "",
    val priority: Priority = Priority.NONE,
    val tags: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)


enum class Priority {
    HIGH, MEDIUM, LOW, NONE
}
