package com.solodev.ideahub.ui.screen.goalScreen
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.ideahub.data.GoalItem
import com.solodev.ideahub.data.GoalsRepository
import com.solodev.ideahub.ui.screen.goalCreationScreen.GoalCreationUiState
import com.solodev.ideahub.util.Tools.Companion.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GoalScreenViewModel @Inject constructor(
    private val goalsRepository: GoalsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalScreenUIState())
    private  val _goalCreationUiState = MutableStateFlow(GoalCreationUiState())
    val uiState: StateFlow<GoalScreenUIState> = _uiState.asStateFlow()
    val goalCreationUiState: StateFlow<GoalCreationUiState> = _goalCreationUiState.asStateFlow()



    private fun moveGoalToAchievedGoalList(goalItem: GoalItem) {
        viewModelScope.launch {
            goalsRepository.updateGoal(goalItem)
        }
       fetchGoals()
    }


    fun deleteGoal(goalItem: GoalItem) {

        viewModelScope.launch {
            goalsRepository.deleteGoal(goalItem)
            fetchGoals()
        }

    }



    fun refreshGoals() {
        fetchGoals()
    }


    fun onEditGoal(toEdit: GoalItem) {
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


    private fun confirmGoalUpdate(updatedGoalItem: GoalItem) {
        viewModelScope.launch {
            goalsRepository.updateGoal(updatedGoalItem)
            refreshGoals()
        }
    }


    private fun fetchGoals() {

        viewModelScope.launch {
            goalsRepository.getGoalsStream().collect{
                goals ->
                _uiState.update { state ->
                    state.copy(

                        achievedGoalItemLists = goals.filter { item-> item.isCompleted },
                        unAchievedGoalItemLists = goals.filter { item-> !item.isCompleted },
                        refresh = true,
                        refresherData = System.currentTimeMillis()
                    )
                }
            }

        }
    }


    fun markGoalAsCompleted(goalItem: GoalItem) {
        moveGoalToAchievedGoalList(goalItem.copy(isCompleted = true))

    }

    init {
        fetchGoals()
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
        val state = _goalCreationUiState.value
        return when {
            state.title.isBlank() -> {
                _goalCreationUiState.update { state -> state.copy(hasError = true, errorMessage = "The title is empty")}
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

    // Create a new goal
    fun createGoal() :GoalItem? {

        if (validateInputs()) {
            val newGoal = copyGoalDataFromState()
            return  newGoal
        }
        else{
            return  null
        }

    }
    private  fun copyGoalDataFromState(isUpdate: Boolean = false):GoalItem {
        val state = _goalCreationUiState.value
        if(!isUpdate)
        {
            val newGoalItem = GoalItem(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                reminderFrequency = state.reminderFrequency,
                creationDate = getCurrentDate(),
                id = System.currentTimeMillis(),
                isCompleted = false,
                delete = false
            )
            return newGoalItem
        }
        else {
            val newGoalItem = GoalItem(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                reminderFrequency = state.reminderFrequency,
                creationDate = state.creationDate,
                id = state.id,
                isCompleted = false,
                delete = false
            )
            return newGoalItem
        }
    }

    fun onGoalCreated(goalItem: GoalItem) {
        viewModelScope.launch {
            goalsRepository.insertGoal(goalItem)
        }

    }

    fun onConfirmDatePickingDialog(date: String){
        _goalCreationUiState.update {
                state -> state.copy(
            deadline = date,
            confirmDateCreation = true
        )
        }
    }


}

data class GoalScreenUIState(
    val achievedGoalItemLists: List<GoalItem> = emptyList(),
    val unAchievedGoalItemLists: List<GoalItem> = emptyList(),
    val refresh: Boolean = false,
    val refresherData: Long = 0,
)


