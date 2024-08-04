package com.solodev.ideahub.ui.screen.goalCreationScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.solodev.ideahub.ui.screen.goalScreen.Goal
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenUIState
import com.solodev.ideahub.ui.screen.goalScreen.goalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class GoalCreationViewModel : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(GoalCreationUiState())
    val uiState: StateFlow<GoalCreationUiState> = _uiState.asStateFlow()
    private val _goalList = MutableStateFlow(GoalScreenUIState())
    val goalList: StateFlow<GoalScreenUIState> = _goalList.asStateFlow()
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
                _uiState.update { state-> state.copy(hasError = true, errorMessage = "title is empty")}
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: ${_uiState.value.errorMessage}")
                false
            }
            state.deadline.isBlank() -> {
                _uiState.update { state-> state.copy(hasError = true, errorMessage = "empty deadline")}
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: ${_uiState.value.errorMessage}")
                false
            }
            !isValidDate(state.deadline) -> {
                _uiState.update { state-> state.copy(hasError = true, errorMessage = "the date is invalide or empty")}
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: ${_uiState.value.errorMessage}")
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
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Date: ${date}")
        if (date.isBlank()) {
            return false
        }
        else
            return true
    }

    // Create a new goal
    fun createGoal() :Goal? {
        if (validateInputs()) {
            val state = _uiState.value ?: return null
            val newGoal = Goal(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                reminderFrequency = state.reminderFrequency,
                creationDate = getCurrentDate(),
                id = System.currentTimeMillis(),
                isCompleted = false,
                delete = false
            )
            return  newGoal
        }
        else{
            return  null
        }

    }

    fun onGoalCreated(goal: Goal){
        goalData.add(goal)
    }

    fun onConfirmDatePickingDialog(date: String){
        _uiState.update {
            state -> state.copy(
                deadline = date,
                confirmDateCreation = true
            )
        }
    }
    // Get the current date in the required format
    private fun getCurrentDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(Date())
    }
}

