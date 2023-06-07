package com.dicoding.bukuku

import com.dicoding.bukuku.response.BookPagingResponse
import com.dicoding.bukuku.response.BookResponse
import com.dicoding.bukuku.response.BooksItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {
    @GET("book")
    suspend fun getAllBooks(
        @Query("p") page: Int,
        @Query("perPage") size: Int
    ): BookPagingResponse

    @GET("book/sortByGenre")
    fun sortByGenre(
        @Query("tag") genre: String
    ): Call<BookResponse>

    @GET("book/{id}")
    fun getBookById(
        @Path("id") id: Int
    ): Call<BooksItem>

//    @FormUrlEncoded
//    @POST("login")
//    fun login(
//        @Field("username") email: String,
//        @Field("password") password: String
//    ): Call<Login>
}