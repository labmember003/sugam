package com.falcon.sugam.network

import com.falcon.sugam.api.SummarizeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = ""

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofitBuilder() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }


    @Provides
    @Singleton
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): SummarizeAPI {
        return retrofitBuilder.build().create(SummarizeAPI::class.java)
    }
}