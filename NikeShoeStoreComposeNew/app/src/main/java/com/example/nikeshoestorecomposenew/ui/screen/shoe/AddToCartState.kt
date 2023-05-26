package com.example.nikeshoestorecomposenew.ui.screen.shoe

import com.example.nikeshoestorecomposenew.data.model.reponse.AddToCartResponse

sealed class AddToCartState {
    object Initial : AddToCartState()
    object Loading : AddToCartState()
    data class Success(val addToCartResponse: AddToCartResponse) : AddToCartState()
    data class Error(val message: String) : AddToCartState()
}