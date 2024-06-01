package com.solodev.ideahub.ui.States

data class ConnectionUiState(
    val userName: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val userEmail: String = "",
)
