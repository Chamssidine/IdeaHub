package com.solodev.ideahub.ui.screen.goalScreen
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GoalScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GoalScreenUIState())
    private  val _goalCreationUiState = MutableStateFlow(GoalCreationUiState())
    val uiState: StateFlow<GoalScreenUIState> = _uiState.asStateFlow()
    val goalCreationUiState: StateFlow<GoalCreationUiState> = _goalCreationUiState.asStateFlow()
    var goal: LiveData<Goal?> =  MutableLiveData(null)


    private fun addGoal(goal: Goal) {
        _uiState.update { state ->
            state.copy(achievedGoalList = state.achievedGoalList + goal)
        }
    }


    fun deleteGoal(goal: Goal) {
        setItemAsDeleted(goal.id)
        Log.d("GoalScreenViewModel", "goalData size: ${goalData.size}")
        _uiState.update { state ->
            state.copy(achievedGoalList = state.achievedGoalList.filterNot { it.id == goal.id })
        }
        Log.d("GoalScreenViewModel", "deleting goal: ${goal.title}")
    }
    fun deleteGoalFromAchievedList(goal: Goal) {
        _uiState.update { state ->
            state.copy(achievedGoalList = state.achievedGoalList.filterNot {it.id == goal.id })
        }
    }

    fun deleteGoalFromUnAchievedList(goal: Goal) {
        _uiState.update { state ->
            state.copy(unAchievedGoalList = state.unAchievedGoalList.filterNot { it.id == goal.id })
        }
        goalData.remove(goal)
    }
    fun refreshGoals() {
        setGoals()
    }

    private fun setItemAsDeleted(id: Long) {
        viewModelScope.launch {
            goalData.find { it.id == id }?.delete = true
        }
    }

    fun onEditGoal(toEdit: Goal) {
        _goalCreationUiState.update {
            state -> state.copy(
            title = toEdit.title,
            description = toEdit.description,
            deadline = toEdit.deadline,
            reminderFrequency = toEdit.reminderFrequency,
            id = toEdit.id
            )
        }

    }
    fun updateGoalInList() {

        confirmGoalUpdate(copyGoalDataFromState(true))
    }


    private fun confirmGoalUpdate(updatedGoal: Goal) {
        if (validateInputs()) {
            _uiState.update { state ->
                state.copy(
                    unAchievedGoalList = state.unAchievedGoalList.map { goal ->
                        if (goal.id == updatedGoal.id) updatedGoal else goal
                    }
                )
            }
        }
        else
            Log.d("GoalScreenViewModel", "The goal is not found in the list")
    }




    private fun setGoals() {
        if (goalData.isNotEmpty()) {
            val nonDeletedGoals = goalData.filterNot { it.delete }
            val achievedGoals = nonDeletedGoals.filter { it.isCompleted }
            val unAchievedGoals = nonDeletedGoals.filter { !it.isCompleted }
            _uiState.update { state ->
                state.copy(
                    achievedGoalList = achievedGoals,
                    unAchievedGoalList = unAchievedGoals,
                    refresh = true,
                    refresherData = System.currentTimeMillis()
                )
            }
        } else {
            Log.d("GoalScreenViewModel", "No goals found")
        }
    }


    fun markGoalAsCompleted(goal: Goal) {
        addGoal(goal.copy(isCompleted = true))
        deleteGoalFromUnAchievedList(goal)

    }

    init {
        setGoals()
    }
    fun updateTitle(newTitle: String) {
        _goalCreationUiState.value = _goalCreationUiState.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _goalCreationUiState.value = _goalCreationUiState.value .copy(description = newDescription)
    }


    fun updateReminderFrequency(newFrequency: String) {
        _goalCreationUiState.value  = _goalCreationUiState.value .copy(reminderFrequency = newFrequency)
    }

    // Validate input fields
    private fun validateInputs(): Boolean {
        val state = _goalCreationUiState.value  ?: return false
        return when {
            state.title.isBlank() -> {
                _goalCreationUiState.update { state-> state.copy(hasError = true, errorMessage = "The title is empty")}
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: ${_goalCreationUiState.value.errorMessage}")
                false
            }
            state.description.isBlank() -> {
                _goalCreationUiState.update { state-> state.copy(hasError = true, errorMessage = "The description is empty")}
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: ${_goalCreationUiState.value.errorMessage}")
                false
            }
            !isValidDate(state.deadline) -> {
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Error: ${_goalCreationUiState.value.errorMessage}")
                false
            }
            else -> {
                _goalCreationUiState.value = state.copy(hasError = false, errorMessage = null)
                true
            }
        }
    }

    // Check if the deadline is a valid date
    private fun isValidDate(date: String): Boolean {
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Date: $date")

        // Define the date format
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        dateFormat.isLenient = false  // Strict date parsing

        try {
            // Parse the input date string
            val parsedDate = dateFormat.parse(date)
            if (parsedDate == null) {
                _goalCreationUiState.update { state -> state.copy(hasError = true, errorMessage = "The date is empty") }
                return false
            }

            // Get the current date
            val currentDate = Calendar.getInstance().time

            // Compare parsed date with current date
            if (parsedDate.before(currentDate)) {
                _goalCreationUiState.update { state -> state.copy(hasError = true, errorMessage = "The date is already passed") }
                return false
            } else {
                return true
            }
        } catch (e: ParseException) {
            // Handle invalid date format
            _goalCreationUiState.update { state -> state.copy(hasError = true, errorMessage = "The date format is invalid") }
            return false
        }
    }

    fun clearUiState() {
        _goalCreationUiState.value = GoalCreationUiState()
    }
    private  fun copyGoalDataFromState(isUpdate: Boolean = false):Goal {
        val state = _goalCreationUiState.value
        if(!isUpdate)
        {
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
            return newGoal
        }
        else {
            val newGoal = Goal(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                reminderFrequency = state.reminderFrequency,
                creationDate = state.creationDate,
                id = state.id,
                isCompleted = false,
                delete = false
                )
            return newGoal
        }
    }
    // Create a new goal
    fun createGoal() :Goal? {

        if (validateInputs()) {
            val newGoal = copyGoalDataFromState()
            return  newGoal
        }
        else{
            return  null
        }

    }

    fun onGoalCreated(goal: Goal) {
        _uiState.update { state ->
            state.copy(
                unAchievedGoalList = state.unAchievedGoalList + goal,
            )
        }
        goalData.add(goal)
    }

    fun onConfirmDatePickingDialog(date: String){
        _goalCreationUiState.update {
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

data class GoalScreenUIState(
    val achievedGoalList: List<Goal> = emptyList(),
    val unAchievedGoalList: List<Goal> = emptyList(),
    val refresh: Boolean = false,
    val refresherData: Long = 0,
)

data class Goal(
    val title: String,
    val description: String,
    val deadline: String,
    val reminderFrequency: String,
    val creationDate: String,
    val id: Long,
    var isCompleted: Boolean,
    var delete: Boolean,
)

val goalData = mutableListOf<Goal>()
