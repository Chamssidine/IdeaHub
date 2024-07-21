package com.solodev.ideahub.ui.screen.sign_up

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val hasError: Boolean = false

)
