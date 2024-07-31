package com.solodev.ideahub.ui.screen.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solodev.ideahub.util.network.ResponseState
import com.solodev.ideahub.util.network.ServerResponse
import com.solodev.ideahub.util.network.ServerStatus
import com.solodev.ideahub.util.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    var userEmail by mutableStateOf("")
    var password by mutableStateOf("")

    private val _loginState = MutableLiveData<Result<Boolean>>()
    val loginState: LiveData<Result<Boolean>> = _loginState

    private fun processUserInput(email: String, password: String): ServerResponse {
        val serverResponse = ServerResponse()

        if (email.isEmpty()) {
            Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "email is empty")
            serverResponse.status = ResponseState(ServerStatus.ERROR)
            serverResponse.message = "Email cannot be empty"
            _uiState.update { currentState ->
                currentState.copy(
                    emailError = true,
                    emailErrorMessage = serverResponse.message.toString()
                )
            }
        }
        if (password.isEmpty()) {
            Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "password is empty")
            serverResponse.message = "Password cannot be empty"
            serverResponse.status = ResponseState(ServerStatus.ERROR)
            _uiState.update { currentState ->
                currentState.copy(
                    passwordError = true,
                    passwordErrorMessage = serverResponse.message.toString()
                )
            }
        }
        if(serverResponse.status != ResponseState(ServerStatus.ERROR))
        {
            onSignInClick(email,password)
        }

        return serverResponse
    }

    private fun onSignInClick(email: String, password: String){
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "onSignInClick")
        viewModelScope.launch {
            try {
                accountService.authenticate(email, password)
                _loginState.value = Result.success(true)
                _uiState.update {
                        state -> state.copy(
                    loginState = LoginState(
                        ConnexionState.Success,
                        "Login success"
                    )
                )
                }

            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
                _uiState.update {
                    state -> state.copy(
                        loginState = LoginState(
                            ConnexionState.Failed,
                            e.message.toString()
                        )
                    )
                }
                _uiState.update {state -> state.copy(loginErrorMessage = e.message.toString()) }
            }
        }

    }

    private  fun OnResetPassword(email: String) {
        viewModelScope.launch {
            try {
                 accountService.sendRecoveryEmail(email)

            }catch (e: Exception) {
                Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "${e.message}")
            }
        }
    }
    fun updateEmail(email: String) {
        userEmail = email
        _uiState.update { state -> state.copy(emailError = false) }
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Updated Email: $userEmail")
    }

    fun updatePassword(password: String) {
        this.password = password
        _uiState.update { state -> state.copy(passwordError = false) }
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Updated Password: $password")
    }
    fun login() {
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "UserEmail: $userEmail")
        Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Password: $password")
        processUserInput(userEmail, password)
    }

    init {
        clearError()
    }

    private fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(
                emailError = false,
                passwordError = false,
                emailErrorMessage = "",
                passwordErrorMessage = ""
            )
        }
    }
}

