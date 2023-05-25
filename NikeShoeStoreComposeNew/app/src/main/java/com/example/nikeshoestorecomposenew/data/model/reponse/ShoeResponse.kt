package com.example.nikeshoestorecomposenew.data.model.reponse

data class ShoeResponse(
    val id: Int,
    val title: String,
    val price: Int,
    val discount: Int,
    val previous_price: Int,
    val image: String,
    val status: Int,
)