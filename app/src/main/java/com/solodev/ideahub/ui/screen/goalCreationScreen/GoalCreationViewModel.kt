package com.solodev.ideahub.ui.screen.goalCreationScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firestore.v1.DocumentDelete
import com.solodev.ideahub.ui.screen.goalScreen.Goal
import com.solodev.ideahub.ui.screen.goalScreen.GoalScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
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
                _uiState.value = state.copy(hasError = true, errorMessage = "Title cannot be empty")
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: $_uiState.value.errorMessage")
                false
            }
            state.deadline.isBlank() -> {
                _uiState.value = state.copy(hasError = true, errorMessage = "Deadline cannot be empty")
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: $_uiState.value.errorMessage")
                false
            }
            !isValidDate(state.deadline) -> {
                _uiState.value = state.copy(hasError = true, errorMessage = "Deadline must be a valid date")
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: $_uiState.value.errorMessage")
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
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "the goal is null")
        return  null
    }

    fun onGoalCreated(goal: Goal){
        if(goal != null){
            val goalList: MutableList<Goal>  = mutableListOf()
            goalList.add(goal)
            _goalList.update {
                state -> state.copy(
                    goalList = goalList
                )
            }
        }
    }

    // Get the current date in the required format
    private fun getCurrentDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(Date())
    }
}

