package com.solodev.ideahub.ui.screen.goalScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.ideahub.util.Tools.Companion.getCurrentDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DayPlanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DayPlanUiState())
    private  val _dayPlanDialogUiState = MutableStateFlow(DayPlanItemUiState())
    val uiState: StateFlow<DayPlanUiState> = _uiState.asStateFlow()
    val dayPlanDialogUIState: StateFlow<DayPlanItemUiState> = _dayPlanDialogUiState.asStateFlow()

    fun createDayPlanItem():DayPlan? {

        if (validateInputs()) {
            val dayPlan = copyDayPlanDataFromState()
            return  dayPlan
        } else
            return  null

    }

    fun addDayPlanItem(item: DayPlan) {

        _uiState.update {  state ->
            state.copy(dayPlans = state.dayPlans + item)
        }
        dayPlans.add(item)
    }

     fun updateDayPlanList() {
        confirmDayPlanUpdate(copyDayPlanDataFromState(true))
    }

    private fun confirmDayPlanUpdate(updatedGoal: DayPlan) {
        Log.d("DayPlanViewModel", "confirmDayPlanUpdate called with updatedGoal: ${updatedGoal.id}")

        if (validateInputs()) {
            _uiState.update { state ->
                state.copy(
                    dayPlans = state.dayPlans.map { item ->
                        if (item.id == updatedGoal.id) {
                            updatedGoal
                        } else item
                    }
                )
            }
            dayPlans.toMutableList().replaceAll {
                if (it.id == updatedGoal.id) {
                    updatedGoal
                } else it
            }
        }
        else
            Log.d("GoalScreenViewModel", "The goal is not found in the list")
    }

    fun clearUiState() {
        _dayPlanDialogUiState.value = DayPlanItemUiState()
    }

    fun toggleCompletion(index: Int) {
        val updatedItems = _uiState.value.dayPlans.toMutableList()
        updatedItems[index] = updatedItems[index].copy(
            isCompleted = !updatedItems[index].isCompleted,
            progress = 100
        )
        _uiState.value = _uiState.value.copy(dayPlans = updatedItems)
    }
    private fun copyDayPlanDataFromState(isUpdate: Boolean = false):DayPlan {
        val state = _dayPlanDialogUiState.value
        if(!isUpdate)
        {
            val newDayPlan = DayPlan(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                priority = state.priority,
                creationDate = getCurrentDate(),
                id = System.currentTimeMillis(),
                isCompleted = false,
                delete = false,
                )
            return newDayPlan
        }
        else {
            val newDayPlan = DayPlan(
                title = state.title,
                description = state.description,
                deadline = state.deadline,
                priority = state.priority,
                creationDate = state.creationDate,
                id = state.id,
                isCompleted = false,
                delete = false,
            )
            return newDayPlan
        }
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
        if(progress < 0 || progress > 100)
            _dayPlanDialogUiState.update {
                state -> state.copy(hasError = true, errorMessage = "Progress must be between 0 and 100")
            }
        else {
            _dayPlanDialogUiState.update {
                    state -> state.copy(progress = progress)
            }
            Log.d("DayPlanViewModel", "updateProgress called with progress: ${_dayPlanDialogUiState.value.progress}")
        }

    }

    fun onEditGoal(toEdit: DayPlan) {
        Log.d("DayPlanViewModel", "onEditGoal called with toEdit: ${toEdit.id}")
        _dayPlanDialogUiState.update {
                state -> state.copy(
                    title = toEdit.title,
                    description = toEdit.description,
                    deadline = toEdit.deadline,
                    priority =  toEdit.priority,
                    id = toEdit.id
                )
        }
        Log.d("DayPlanViewModel", "onEditGoal called with toEdit: ${toEdit.progress}")
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

    private  fun fetchDayPlans(): MutableList<DayPlan> {
        return dayPlans
    }

    fun deletePlan(dayPlan: DayPlan){
        setItemAsDeleted(dayPlan.id)
        _uiState.update {
            state -> state.copy(dayPlans = state.dayPlans.filterNot { it.id == dayPlan.id })
        }
        dayPlans.remove(dayPlan)
    }

    private fun setItemAsDeleted(id: Long) {
        viewModelScope.launch {
            dayPlans.find { it.id == id }?.delete = true
        }
    }

    init {
        setDayPlansData()
    }

    private fun  setDayPlansData() {
        _uiState.update {
            state -> state.copy(dayPlans = fetchDayPlans())
        }
    }
}

val dayPlans = mutableListOf<DayPlan>()

data class DayPlanUiState(
    val dayPlans: List<DayPlan> = emptyList(),
    val hasError: Boolean = false,
)

data class DayPlanItemUiState (
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val creationDate: String = "",
    val deadline: String = "",
    val priority: Priority = Priority.NONE,
    val tags: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    var delete: Boolean = false,
)
data class DayPlan (
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val creationDate: String = "",
    val deadline: String = "",
    val priority: Priority = Priority.NONE,
    val tags: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    var delete: Boolean = false,
)


enum class Priority {
    HIGH, MEDIUM, LOW, NONE
}
