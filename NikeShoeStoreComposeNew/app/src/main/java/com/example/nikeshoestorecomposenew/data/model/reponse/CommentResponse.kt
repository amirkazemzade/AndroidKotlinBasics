package com.example.nikeshoestorecomposenew.data.model.reponse

data class CommentResponse(
    val author: Author,
    val content: String,
    val date: String,
    val id: Int,
    val title: String,
)