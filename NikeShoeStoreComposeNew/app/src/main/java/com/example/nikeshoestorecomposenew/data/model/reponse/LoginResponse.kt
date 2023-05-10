package com.example.nikeshoestorecomposenew.data.model.reponse

data class LoginResponse(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)