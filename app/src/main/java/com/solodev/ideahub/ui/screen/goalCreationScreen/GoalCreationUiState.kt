package com.solodev.ideahub.ui.screen.goalCreationScreen

data class GoalCreationUiState(
    val title: String = "",
    val description: String = "",
    val deadline: String = "",
    val reminderFrequency: String = "",
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val creationDate: String = "",
    val confirmDateCreation: Boolean = false,
    val id: Long = 0
)
