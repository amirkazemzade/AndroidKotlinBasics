package com.example.nikeshoestorecomposenew.ui.screen.auth

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nikeshoestorecomposenew.data.model.error.ErrorResponse
import com.example.nikeshoestorecomposenew.data.model.request.LoginRequest
import com.example.nikeshoestorecomposenew.data.model.request.SignUpRequest
import com.example.nikeshoestorecomposenew.data.service.DataStoreService
import com.example.nikeshoestorecomposenew.data.service.createApiService
import com.example.nikeshoestorecomposenew.data.service.createMoshi
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _apiService = createApiService()

    private val _authState = mutableStateOf<AuthState>(AuthState.Initial)
    val authState: State<AuthState> = _authState

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _authType = mutableStateOf(AuthType.Login)
    val authType: State<AuthType> = _authType

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun changeAuthType() {
        if (authType.value == AuthType.Login) {
            _authType.value = AuthType.SignUp
        } else {
            _authType.value = AuthType.Login
        }
    }

    private fun signUp(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = _apiService.signUp(SignUpRequest(email.value, password.value))
            if (response.isSuccessful) {
                _authState.value = AuthState.SignUpSuccess(response.body()!!)
                login(context)
            } else {
                val moshi = createMoshi()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                _authState.value = AuthState.Error(errorResponse!!.message)
            }
        }
    }

    private fun login(context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val response = _apiService.login(LoginRequest(email.value, password.value))
            if (response.isSuccessful) {
                val loginResponse = response.body()!!
                _authState.value = AuthState.LoginSuccess(loginResponse)
                val dataStoreService = DataStoreService(context)
                dataStoreService.setUserEmail(email.value)
                dataStoreService.setUserToken(loginResponse.access_token)
            } else {
                val moshi = createMoshi()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val errorResponse = adapter.fromJson(response.errorBody()!!.string())
                _authState.value = AuthState.Error(errorResponse!!.message)
            }
        }
    }

    fun auth(context: Context) {
        if (authType.value == AuthType.Login) {
            login(context)
        } else {
            signUp(context)
        }
    }
}

enum class AuthType {
    Login,
    SignUp
}