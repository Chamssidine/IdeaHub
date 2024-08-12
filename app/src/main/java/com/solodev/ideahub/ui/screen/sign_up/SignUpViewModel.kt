package com.solodev.ideahub.ui.screen.sign_up

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.solodev.ideahub.util.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    var uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    var userEmail by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("")

    private val _loginState = MutableLiveData<Result<Boolean>>()
    val loginState: LiveData<Result<Boolean>> = _loginState


    fun onSignUpClick() {
        if (name.isEmpty()) {
            _uiState.update { state -> state.copy(hasError = true) }
            Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Empty Name")
            _uiState.update { state -> state.copy(errorMessage = "Name could not be empty") }
        } else if (userEmail.isEmpty()) {
            _uiState.update { state -> state.copy(hasError = true) }
            Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Empty Name")
            _uiState.update { state -> state.copy(errorMessage = "email could not be empty") }
        } else if (password.isEmpty()) {
            _uiState.update { state -> state.copy(hasError = true) }
            Log.d("com.solodev.ideahub.ui.screen.login.ConnectionViewModel", "Empty Name")
            _uiState.update { state -> state.copy(errorMessage = "password could not be empty") }
        } else {

            signUp(name, userEmail, password)


        }

    }

    private fun signUp(name: String, email: String, password: String) {

        viewModelScope.launch {
            try {
                accountService.register(email, password, name)
                _loginState.value = Result.success(true)
                _uiState.update { state -> state.copy(isAccountCreationSuccessful = true) }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
                _uiState.update { state -> state.copy(hasError = true) }
                _uiState.update { state -> state.copy(errorMessage = e.message) }
                Log.d(
                    "com.solodev.ideahub.ui.screen.login.ConnectionViewModel",
                    "error: $e.message"
                )
            }
        }

}
    fun updateEmail(email: String) {
        userEmail = email
        _uiState.update {
            state -> state.copy(
                hasError = false,
                email = email
            )

        }
        Log.d(
            "com.solodev.ideahub.ui.screen.login.ConnectionViewModel",
            "Updated Email: $userEmail"
        )
    }

    fun updatePassword(password: String) {
        this.password = password
        _uiState.update {
            state -> state.copy(
                hasError = false,
                password = password
            )
        }
        Log.d(
            "com.solodev.ideahub.ui.screen.login.ConnectionViewModel",
            "Updated Password: $password"
        )
    }
    fun updateName(name: String) {
        this.name = name
        _uiState.update { state -> state.copy(
            hasError = false,
            name = name
        ) }
        Log.d(
            "com.solodev.ideahub.ui.screen.login.ConnectionViewModel",
            "Updated Password: $password"
        )
    }
}