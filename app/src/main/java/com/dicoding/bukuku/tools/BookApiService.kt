package com.dicoding.bukuku.tools

import com.dicoding.bukuku.response.AuthorizationResponse
import com.dicoding.bukuku.response.BookPagingResponse
import com.dicoding.bukuku.response.BookResponse
import com.dicoding.bukuku.response.BooksItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("book/details/{id}")
    fun getBookById(
        @Path("id") id: Int
    ): Call<BooksItem>

    @GET("book/search")
    fun searchBook(
        @Query("name") query: String
    ): Call<BookResponse>

    @FormUrlEncoded
    @POST("user/regis")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<AuthorizationResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<AuthorizationResponse>

//    @FormUrlEncoded
//    @POST("login")
//    fun login(
//        @Field("username") email: String,
//        @Field("password") password: String
//    ): Call<Login>
}