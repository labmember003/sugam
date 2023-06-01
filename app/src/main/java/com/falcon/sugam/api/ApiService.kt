package com.falcon.sugam

import com.falcon.sugam.Constants.BASE_URL
import com.falcon.sugam.api.UserRequest
import com.falcon.sugam.api.UserResponse
import retrofit2.Retrofit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder().build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface SummarizeAPI {
    @POST("/upload")
    suspend fun getSummarizedText(@Body userRequest: UserRequest): Response<UserResponse>

//    @POST()
//    suspend fun getSummarizedText(@Url url: String): Response<UserResponse>
}

object Api {
    val fileApiService : SummarizeAPI by lazy {
        retrofit.create(SummarizeAPI::class.java)
    }
}