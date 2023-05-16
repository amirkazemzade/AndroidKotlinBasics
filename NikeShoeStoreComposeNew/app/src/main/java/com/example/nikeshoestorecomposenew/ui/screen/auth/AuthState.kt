package com.example.nikeshoestorecomposenew.ui.screen.auth

import com.example.nikeshoestorecomposenew.data.model.reponse.LoginResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.SignUpResponse

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    data class SignUpSuccess(val response: SignUpResponse) : AuthState()
    data class LoginSuccess(val response: LoginResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}
