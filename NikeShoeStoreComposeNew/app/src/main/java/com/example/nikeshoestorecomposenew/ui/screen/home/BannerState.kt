package com.example.nikeshoestorecomposenew.ui.screen.home

import com.example.nikeshoestorecomposenew.data.model.reponse.BannerResponse

sealed class BannerState {
    object Initial : BannerState()
    object Loading : BannerState()
    data class Success(val banners: List<BannerResponse>) : BannerState()
    data class Error(val message: String) : BannerState()
}