package com.solodev.ideahub.ui.screen.goalCreationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoalCreationViewModel : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(GoalCreationUiState())
    val uiState: StateFlow<GoalCreationUiState> = _uiState.asStateFlow()

    // Update functions for each field
    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    fun updateDeadline(newDeadline: String) {
        _uiState.value = _uiState.value.copy(deadline = newDeadline)
    }

    fun updateReminderFrequency(newFrequency: String) {
        _uiState.value = _uiState.value.copy(reminderFrequency = newFrequency)
    }

    // Validate input fields
    private fun validateInputs(): Boolean {
        val state = _uiState.value ?: return false
        return when {
            state.title.isBlank() -> {
                _uiState.value = state.copy(hasError = true, errorMessage = "Title cannot be empty")
                false
            }
            state.deadline.isBlank() -> {
                _uiState.value = state.copy(hasError = true, errorMessage = "Deadline cannot be empty")
                false
            }
            !isValidDate(state.deadline) -> {
                _uiState.value = state.copy(hasError = true, errorMessage = "Deadline must be a valid date")
                false
            }
            else -> {
                _uiState.value = state.copy(hasError = false, errorMessage = null)
                true
            }
        }
    }

    // Check if the deadline is a valid date
    private fun isValidDate(date: String): Boolean {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.parse(date) != null
        } catch (e: Exception) {
            false
        }
    }

    // Create a new goal
    fun createGoal(onGoalCreated: (Goal) -> Unit) {
        if (validateInputs()) {
            val state = _uiState.value ?: return
            val newGoal = Goal(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                reminderFrequency = state.reminderFrequency,
                creationDate = getCurrentDate()
            )
            onGoalCreated(newGoal)
        }
    }

    // Get the current date in the required format
    private fun getCurrentDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(Date())
    }
}

// Data class for Goal
data class Goal(
    val title: String,
    val description: String,
    val deadline: String,
    val reminderFrequency: String,
    val creationDate: String
)
