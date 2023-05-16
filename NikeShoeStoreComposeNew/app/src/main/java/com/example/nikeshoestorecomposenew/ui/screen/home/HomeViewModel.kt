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

    private var _shoesListState = mutableStateOf<ShoesListState>(ShoesListState.Initial)
    val shoesListState: State<ShoesListState> = _shoesListState

    fun getBanners(context: Context) {
        viewModelScope.launch {
            _bannerState.value = BannerState.Loading
            val dataStoreService = DataStoreService(context)
            dataStoreService.getUserToken.onEach { token ->
                if (token.isNotBlank()) {
                    val response = _apiService.getBanners("Bearer $token")
                    if (response.isSuccessful) {
                        _bannerState.value = BannerState.Success(response.body()!!)
                    } else {
                        val moshi = createMoshi()
                        val adapter = moshi.adapter(ErrorResponse::class.java)
                        val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                        _bannerState.value = BannerState.Error(errorResponse!!.message)
                    }
                    return@onEach
                }
            }.collect()
        }
    }

    fun getNewestShoes(context: Context) {
        viewModelScope.launch {
            _shoesListState.value = ShoesListState.Loading
            val dataStoreService = DataStoreService(context)
            dataStoreService.getUserToken.onEach { token ->
                if (token.isNotBlank()) {
                    val response = _apiService.getNewestShoes("Bearer $token")
                    if (response.isSuccessful) {
                        _shoesListState.value = ShoesListState.Success(response.body()!!)
                    } else {
                        val moshi = createMoshi()
                        val adapter = moshi.adapter(ErrorResponse::class.java)
                        val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                        _shoesListState.value = ShoesListState.Error(errorResponse!!.message)
                    }
                    return@onEach
                }
            }.collect()
        }
    }
}