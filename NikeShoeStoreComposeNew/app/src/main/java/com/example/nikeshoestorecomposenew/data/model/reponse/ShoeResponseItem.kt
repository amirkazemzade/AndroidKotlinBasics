package com.example.nikeshoestorecomposenew.data.model.reponse

data class ShoeResponseItem(
    val discount: Int,
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String,
)