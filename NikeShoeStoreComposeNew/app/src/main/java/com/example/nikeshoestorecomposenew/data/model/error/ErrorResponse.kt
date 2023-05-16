package com.example.nikeshoestorecomposenew.data.model.error

data class ErrorResponse(
    val error: String,
    val message: String,
    val hint: String?,
)