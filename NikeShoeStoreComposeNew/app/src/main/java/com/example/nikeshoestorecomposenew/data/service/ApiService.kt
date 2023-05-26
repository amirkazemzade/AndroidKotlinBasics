package com.example.nikeshoestorecomposenew.data.service

import android.annotation.SuppressLint
import com.example.nikeshoestorecomposenew.BASE_URL
import com.example.nikeshoestorecomposenew.data.model.reponse.AddToCartResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.BannerResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.CommentResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.LoginResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.ShoeResponse
import com.example.nikeshoestorecomposenew.data.model.reponse.SignUpResponse
import com.example.nikeshoestorecomposenew.data.model.request.AddToCartRequest
import com.example.nikeshoestorecomposenew.data.model.request.LoginRequest
import com.example.nikeshoestorecomposenew.data.model.request.SignUpRequest
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

interface ApiService {
    @POST("user/register")
    suspend fun signUp(@Body body: SignUpRequest): Response<SignUpResponse>

    @POST("auth/token")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @GET("banner/slider")
    suspend fun fetchBanners(@Header("Authorization") token: String): Response<List<BannerResponse>>

    @GET("product/list?sort=4")
    suspend fun fetchNewestShoes(@Header("Authorization") token: String): Response<List<ShoeResponse>>

    @GET("comment/list")
    suspend fun fetchComments(@Query("product_id") productId: Int): Response<List<CommentResponse>>

    @POST("cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body body: AddToCartRequest,
    ): Response<AddToCartResponse>
}

fun createMoshi(): Moshi {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}

fun createApiService(): ApiService {
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

    val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .build()

    return retrofit.create(ApiService::class.java)
}