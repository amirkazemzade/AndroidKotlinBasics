package com.example.nikeshoestorecomposenew.ui.screen.auth

import com.example.nikeshoestorecomposenew.data.model.reponse.LoginResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.SignUpResponse

sealed class SignUpState {
    object Initial : SignUpState()
    object Loading : SignUpState()
    data class Success(val response: SignUpResponse) : SignUpState()
    data class Error(val message: String) : SignUpState()
}

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}
