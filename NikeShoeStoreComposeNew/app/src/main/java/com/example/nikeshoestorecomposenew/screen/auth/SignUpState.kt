package com.example.nikeshoestorecomposenew.screen.auth

import com.example.nikeshoestorecomposenew.data.model.response.SignUpResponse

sealed class SignUpState {
    object Initial : SignUpState()
    object Loading : SignUpState()
    data class Success(val data: SignUpResponse) : SignUpState()
    data class Error(val message: String) : SignUpState()
}