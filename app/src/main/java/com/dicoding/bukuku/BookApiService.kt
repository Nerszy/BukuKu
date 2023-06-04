package com.dicoding.bukuku

import com.dicoding.bukuku.response.BookResponse
import retrofit2.Call
import retrofit2.http.GET

interface BookApiService {
    @GET("book")
    fun getBooks(): Call<BookResponse>

//    @FormUrlEncoded
//    @POST("login")
//    fun login(
//        @Field("username") email: String,
//        @Field("password") password: String
//    ): Call<Login>
}