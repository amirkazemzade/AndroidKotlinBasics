package com.example.nikeshoestorecomposenew.ui.screen.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikeshoestorecomposenew.data.model.error.ErrorResponse
import com.example.nikeshoestorecomposenew.data.service.DataStoreService
import com.example.nikeshoestorecomposenew.data.service.createApiService
import com.example.nikeshoestorecomposenew.data.service.createMoshi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _apiService = createApiService()

    private var _bannerState = mutableStateOf<BannerState>(BannerState.Initial)
    val bannerState: State<BannerState> = _bannerState

    private var _newestShoesState = mutableStateOf<ShoeState>(ShoeState.Initial)
    val newestShoesState: State<ShoeState> = _newestShoesState

    fun fetchBanners(context: Context) {
        viewModelScope.launch {
            val dataStoreService = DataStoreService(context)
            val tokenFlow = dataStoreService.getUserToken
            tokenFlow.onEach { token ->
                if (token.isEmpty()) return@onEach
                _bannerState.value = BannerState.Loading
                val response = _apiService.fetchBanners(token)
                if (response.isSuccessful) {
                    _bannerState.value = BannerState.Success(banners = response.body()!!)
                } else {
                    val moshi = createMoshi()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                    _bannerState.value = BannerState.Error(errorResponse!!.message)
                }
            }.collect()
        }
    }

    fun fetchNewestShoes(context: Context) {
        viewModelScope.launch {
            val dataStoreService = DataStoreService(context)
            val tokenFlow = dataStoreService.getUserToken
            tokenFlow.onEach { token ->
                if (token.isEmpty()) return@onEach
                _newestShoesState.value = ShoeState.Loading
                val response = _apiService.fetchNewestShoes(token)
                if (response.isSuccessful) {
                    _newestShoesState.value = ShoeState.Success(shoes = response.body()!!)
                } else {
                    val moshi = createMoshi()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                    _newestShoesState.value = ShoeState.Error(errorResponse!!.message)
                }
            }.collect()
        }
    }
}