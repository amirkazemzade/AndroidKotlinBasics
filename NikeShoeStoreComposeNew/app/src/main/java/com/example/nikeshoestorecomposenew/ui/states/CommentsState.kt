package com.example.nikeshoestorecomposenew.ui.states

import com.example.nikeshoestorecomposenew.data.model.reponse.CommentResponse

sealed class CommentsState {
    object Initial : CommentsState()
    object Loading : CommentsState()
    data class Success(val comments: List<CommentResponse>) : CommentsState()
    data class Error(val message: String) : CommentsState()
}
