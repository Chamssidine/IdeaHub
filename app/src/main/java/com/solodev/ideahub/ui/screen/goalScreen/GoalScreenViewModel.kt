    package com.solodev.ideahub.ui.screen.goalScreen

    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.launch


    class GoalScreenViewModel : ViewModel() {

        private val _uiState = MutableStateFlow(GoalScreenUIState())
        val uiState: StateFlow<GoalScreenUIState> = _uiState.asStateFlow()
        fun addGoal(goal: Goal) {
            val goalList = _uiState.value.goalList.toMutableList()
            goalList.add(goal)
            _uiState.value = _uiState.value.copy(goalList = goalList)
        }

        fun deleteGoal(goal: Goal) {
            setItemAsDeleted(goal.id)
            Log.d("GoalScreenViewModel", "goalData size: ${goalData.size}")
            val updatedGoalList = _uiState.value.goalList.toMutableList().apply {
                remove(goal)
            }
            _uiState.value = _uiState.value.copy(goalList = updatedGoalList)
            Log.d("GoalScreenViewModel", "deleting goal: ${goal.title}")
        }

        private fun setItemAsDeleted(id: Long) {
            viewModelScope.launch {

                goalData.forEachIndexed { index, goal ->
                    if(goal.id == id)
                    {
                        goalData[index].delete = true
                    }
                }
            }

        }

        fun refreshGoals()  {
            goalData.forEachIndexed { index, goal ->
                Log.d("GoalCreationViewModel", "Goal: ${goal.title}")
                Log.d("GoalCreationViewModel", "Goal: ${goal.creationDate}")
                Log.d("GoalCreationViewModel", "Goal: ${goal.deadline}")
                Log.d("GoalCreationViewModel", "Goal: ${goal.reminderFrequency}")
                Log.d("GoalCreationViewModel", "Goal: ${goal.id}")
                Log.d("GoalCreationViewModel", "Goal: ${goal.isCompleted}")
            }
        }
        private fun setGoals()  {
            if(goalData.isNotEmpty()) {
                goalData.forEachIndexed { index, goal ->
                    if(!goal.delete)
                    {
                        _uiState.update { state ->
                            state.copy(goalList = goalData)
                        }
                    }
                }

            }
            else
                Log.d("GoalScreenViewModel", "No goals found")
        }
        init {
            setGoals()

        }
    }



    data class GoalScreenUIState(
        val goalList: MutableList<Goal> = mutableListOf(),
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