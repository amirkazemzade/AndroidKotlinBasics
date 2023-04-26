package com.example.nikeshoestorecomposenew.screen.auth

import com.example.nikeshoestorecomposenew.data.model.response.LoginResponse

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}