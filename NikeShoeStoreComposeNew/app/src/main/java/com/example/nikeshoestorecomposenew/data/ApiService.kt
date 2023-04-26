package com.example.nikeshoestorecomposenew.data

import android.annotation.SuppressLint
import com.example.nikeshoestorecomposenew.data.model.request.LoginBody
import com.example.nikeshoestorecomposenew.data.model.request.SignUpBody
import com.example.nikeshoestorecomposenew.data.model.response.LoginResponse
import com.example.nikeshoestorecomposenew.data.model.response.SignUpResponse
import com.example.nikeshoestorecomposenew.util.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

interface ApiService {
    @POST("auth/token")
    suspend fun login(@Body loginBody: LoginBody): LoginResponse

    @POST("user/register")
    suspend fun signUp(@Body signUpBody: SignUpBody): SignUpResponse
}

fun createApiServiceInstance(): ApiService {

    val trustManager = @SuppressLint("CustomX509TrustManager")
    object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, arrayOf(trustManager), SecureRandom())

    val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .hostnameVerifier { _, _ -> true }
        .build()

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    return retrofit.create(ApiService::class.java)
}