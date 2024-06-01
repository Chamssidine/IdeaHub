package com.solodev.ideahub.ui.ViewModels

import androidx.lifecycle.ViewModel
import com.solodev.ideahub.ui.States.ConnectionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConnectionViewModel: ViewModel() {

    private  val _uiState = MutableStateFlow(ConnectionUiState())
    val uiState: StateFlow<ConnectionUiState> = _uiState.asStateFlow()


}