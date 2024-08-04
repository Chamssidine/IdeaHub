package com.solodev.ideahub.ui.ViewModels

import androidx.lifecycle.ViewModel
import com.solodev.ideahub.ui.States.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class ScreenManagerVM @Inject constructor(

): ViewModel() {
    private  val _uiState = MutableStateFlow(ScreenUiState())
    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()

    fun setFirstLaunch(isFirstLaunch: Boolean) {
        _uiState.value = _uiState.value.copy(isFirstLaunch = isFirstLaunch)
    }
    fun showBottomNavigationBar(showBottomNavigationBar: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomNavigationBar = showBottomNavigationBar)
    }
    fun setDestination(destination: String) {
        _uiState.value = _uiState.value.copy(startDestination = destination)

    }


}