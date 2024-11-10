package com.solodev.ideahub.ui.States

import com.solodev.ideahub.ui.screen.Routes

data class ScreenUiState(
    val isFirstLaunch: Boolean = true,
    val showBottomNavigationBar: Boolean = true,
    val startDestination: String = Routes.Home.name,
    val showCongratulationTextAfterFirstGoalCreation: Boolean = false,
)
