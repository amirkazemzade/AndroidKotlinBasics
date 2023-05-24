package com.example.nikeshoestorecomposenew.ui.screen.home

import com.example.nikeshoestorecomposenew.data.model.reponse.ShoeResponse

sealed class ShoeState {
    object Initial : ShoeState()
    object Loading : ShoeState()
    data class Success(val shoes: List<ShoeResponse>) : ShoeState()
    data class Error(val message: String) : ShoeState()
}