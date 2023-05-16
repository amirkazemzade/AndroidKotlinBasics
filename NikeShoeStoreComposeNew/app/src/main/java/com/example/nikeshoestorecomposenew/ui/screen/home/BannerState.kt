package com.example.nikeshoestorecomposenew.ui.screen.home

import com.example.nikeshoestorecomposenew.data.model.reponse.BannerResponseItem

sealed class BannerState {
    object Initial : BannerState()
    object Loading : BannerState()
    data class Success(val response: List<BannerResponseItem>) : BannerState()
    data class Error(val message: String) : BannerState()
}