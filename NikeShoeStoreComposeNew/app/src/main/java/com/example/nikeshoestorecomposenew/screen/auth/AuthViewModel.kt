package com.example.nikeshoestorecomposenew.screen.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
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
}

enum class AuthType {
    Login,
    SignUp
}