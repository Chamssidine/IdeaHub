package com.solodev.ideahub.ui.screen.login


data class LoginUIState(
    val userName: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val userEmail: String = "",
    val emailErrorMessage: String = "",
    val userNameErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val emailError: Boolean = false,
    val userNameError: Boolean = false,
    val passwordError: Boolean = false,
    val loginErrorMessage: String = "",
    val loginState: LoginState? = null,

)

data class LoginState(
    val state: ConnexionState,
    val message: String
)

enum class ConnexionState{
    Success,
    Failed,
    Loading
}



