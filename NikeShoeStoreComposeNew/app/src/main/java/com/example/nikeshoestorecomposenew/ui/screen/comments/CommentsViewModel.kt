package com.example.nikeshoestorecomposenew.ui.screen.comments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikeshoestorecomposenew.data.model.error.ErrorResponse
import com.example.nikeshoestorecomposenew.data.service.createApiService
import com.example.nikeshoestorecomposenew.data.service.createMoshi
import com.example.nikeshoestorecomposenew.ui.states.CommentsState
import kotlinx.coroutines.launch

class CommentsViewModel : ViewModel() {
    private val _apiService = createApiService()

    private val _commentsState = mutableStateOf<CommentsState>(CommentsState.Initial)
    val commentsState: State<CommentsState> = _commentsState

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
}