package com.solodev.ideahub.ui.States

import com.solodev.ideahub.ui.screen.Routes

data class ScreenUiState(
    val isFirstLaunch: Boolean = true,
    val showBottomNavigationBar: Boolean = false,
    val startDestination: String = Routes.GoalCreation.name,
    val showCongratulationTextAfterFirstGoalCreation: Boolean = false,
)
