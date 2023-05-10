package com.example.nikeshoestorecomposenew.data.model.request

data class LoginRequest(
    val username: String,
    val password: String,
    val grant_type: String = "password",
    val client_secret: String = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC",
    val client_id: Int = 2,
)