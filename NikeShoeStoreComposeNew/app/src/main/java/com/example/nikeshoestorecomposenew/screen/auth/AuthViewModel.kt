package com.example.nikeshoestorecomposenew.screen.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikeshoestorecomposenew.data.createApiServiceInstance
import com.example.nikeshoestorecomposenew.data.model.request.LoginBody
import com.example.nikeshoestorecomposenew.data.model.request.SignUpBody
import com.example.nikeshoestorecomposenew.util.DEFAULT_ERROR_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthViewModel : ViewModel() {
    private val _apiService = createApiServiceInstance()

    private val _loginResult = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginResult: StateFlow<LoginState> = _loginResult

    private val _signUpResult = MutableStateFlow<SignUpState>(SignUpState.Initial)
    val signUpResult: StateFlow<SignUpState> = _signUpResult

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _authType = mutableStateOf(AuthType.Login)
    val authType: State<AuthType> = _authType

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun changeAuthType() {
        if (authType.value == AuthType.Login) {
            _authType.value = AuthType.SignUp
        } else {
            _authType.value = AuthType.Login
        }
    }

    private fun login(username: String, password: String) {
        _loginResult.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val loginBody = LoginBody(username, password)
                val response = _apiService.login(loginBody)
                _loginResult.value = LoginState.Success(response)
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
                Log.d("login error", errorMessage)
                _loginResult.value = LoginState.Error(errorMessage)
            } catch (e: Exception) {
                _loginResult.value = LoginState.Error(DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    private fun signUp(email: String, password: String) {
        _signUpResult.value = SignUpState.Loading
        viewModelScope.launch {
            try {
                val signUpBody = SignUpBody(email, password)
                val response = _apiService.signUp(signUpBody)
                _signUpResult.value = SignUpState.Success(response)
            } catch (e: Exception) {
                _signUpResult.value = SignUpState.Error(e.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    fun auth() {
        if (authType.value == AuthType.Login) {
            login(email.value, password.value)
        } else {
            signUp(email.value, password.value)
        }
    }
}

