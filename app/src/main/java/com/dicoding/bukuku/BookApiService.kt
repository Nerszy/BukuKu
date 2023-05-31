package com.dicoding.bukuku

import retrofit2.Call
import retrofit2.http.GET

interface BookApiService {
    @GET("book")
    fun getBooks(): Call<BookResponseItem>
}