package com.example.nikeshoestorecomposenew.ui.screen.shoe

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikeshoestorecomposenew.data.model.error.ErrorResponse
import com.example.nikeshoestorecomposenew.data.model.request.AddToCartRequest
import com.example.nikeshoestorecomposenew.data.service.DataStoreService
import com.example.nikeshoestorecomposenew.data.service.createApiService
import com.example.nikeshoestorecomposenew.data.service.createMoshi
import com.example.nikeshoestorecomposenew.ui.states.CommentsState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ShoeViewModel : ViewModel() {
    private val _apiService = createApiService()

    private val _commentsState = mutableStateOf<CommentsState>(CommentsState.Initial)
    val commentsState: State<CommentsState> = _commentsState

    private val _addTo_cartState = mutableStateOf<AddToCartState>(AddToCartState.Initial)
    val addToCartState: State<AddToCartState> = _addTo_cartState

    fun fetchDocuments(productId: Int) {
        viewModelScope.launch {
            _commentsState.value = CommentsState.Loading
            val response = _apiService.fetchComments(productId)
            if (response.isSuccessful) {
                _commentsState.value = CommentsState.Success(response.body()!!)
            } else {
                val moshi = createMoshi()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                _commentsState.value = CommentsState.Error(message = errorResponse!!.message)
            }
        }
    }

    fun addToCart(productId: Int, context: Context) {
        viewModelScope.launch {
            _addTo_cartState.value = AddToCartState.Loading
            val dataStoreService = DataStoreService(context)
            val tokenFlow = dataStoreService.getUserToken
            tokenFlow.onEach { token ->
                if (token.isEmpty()) return@onEach
                val response = _apiService.addToCart(
                    token = "Bearer $token",
                    body = AddToCartRequest(product_id = productId)
                )
                if (response.isSuccessful) {
                    _addTo_cartState.value = AddToCartState.Success(response.body()!!)
                } else {
                    val moshi = createMoshi()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                    _addTo_cartState.value =
                        AddToCartState.Error(message = errorResponse!!.message)
                }
            }.collect()
        }
    }
}