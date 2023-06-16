package com.dicoding.bukuku.tools

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()
    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)

    fun getApiServices(): BookApiService {
        val retrofit = retrofitBuilder
            .baseUrl("https://bukuku-46zlg2v3pa-et.a.run.app/")
            .build()
        return retrofit.create(BookApiService::class.java)
    }

    fun getApiServicesRecoomend(): BookRecommendService {
        val retrofit = retrofitBuilder
            .baseUrl("https://ml-46zlg2v3pa-et.a.run.app/")
            .build()
        return retrofit.create(BookRecommendService::class.java)
    }
}