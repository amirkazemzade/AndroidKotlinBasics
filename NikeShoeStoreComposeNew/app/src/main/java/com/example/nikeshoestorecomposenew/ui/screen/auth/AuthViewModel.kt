package com.example.nikeshoestorecomposenew.ui.screen.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikeshoestorecomposenew.data.model.error.ErrorResponse
import com.example.nikeshoestorecomposenew.data.model.request.LoginRequest
import com.example.nikeshoestorecomposenew.data.model.request.SignUpRequest
import com.example.nikeshoestorecomposenew.data.service.createApiService
import com.example.nikeshoestorecomposenew.data.service.createMoshi
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _apiService = createApiService()

    private val _signUpState = mutableStateOf<SignUpState>(SignUpState.Initial)
    val signUpState: State<SignUpState> = _signUpState

    private val _loginState = mutableStateOf<LoginState>(LoginState.Initial)
    val loginState: State<LoginState> = _loginState

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

    private fun signUp() {
        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading
            val response = _apiService.signUp(SignUpRequest(email.value, password.value))
            if (response.isSuccessful) {
                _signUpState.value = SignUpState.Success(response.body()!!)
            } else {
                val moshi = createMoshi()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                _signUpState.value = SignUpState.Error(errorResponse!!.message)
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val response = _apiService.login(LoginRequest(email.value, password.value))
            if (response.isSuccessful) {
                _loginState.value = LoginState.Success(response.body()!!)
            } else {
                val moshi = createMoshi()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                _loginState.value = LoginState.Error(errorResponse!!.message)
            }
        }
    }

    fun auth() {
        if (authType.value == AuthType.Login) {
            login()
        } else {
            signUp()
        }
    }
}

enum class AuthType {
    Login,
    SignUp
}