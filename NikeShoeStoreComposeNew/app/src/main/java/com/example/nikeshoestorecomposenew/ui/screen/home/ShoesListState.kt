package com.example.nikeshoestorecomposenew.ui.screen.home

import com.example.nikeshoestorecomposenew.data.model.reponse.ShoeResponseItem

sealed class ShoesListState {
    object Initial : ShoesListState()
    object Loading : ShoesListState()
    data class Success(val response: List<ShoeResponseItem>) : ShoesListState()
    data class Error(val message: String) : ShoesListState()
}