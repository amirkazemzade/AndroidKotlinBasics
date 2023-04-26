package com.example.nikeshoestorecomposenew.data.model.request

data class LoginBody(
    val username: String,
    val password: String,
    val grant_type: String = "password",
    val client_id: Int = 2,
    val client_secret: String = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC",
)
