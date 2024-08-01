package com.solodev.ideahub.ui.screen.goalScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class GoalScreenViewModel: ViewModel()   {
}



data class GoalScreenUIState(
    val goalList: MutableList<Goal> = GoalData,
)

data class Goal(
    val title: String,
    val description: String,
    val deadline: String,
    val reminderFrequency: String,
    val creationDate: String,
    val id: Long,
    val isCompleted: Boolean,
    val delete: Boolean,
)

val GoalData = mutableListOf<Goal>()