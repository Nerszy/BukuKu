package com.dicoding.bukuku.tools

import com.dicoding.bukuku.RecommendRequest
import com.dicoding.bukuku.RecommendResponse
import com.dicoding.bukuku.response.*
import retrofit2.Call
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("library/create")
    fun postLibrary(
        @Field("username") username: String,
        @Field("books_id") books_id: String
    ): Call<PostLibraryResponse>


    @GET("populate")
    fun getLibrary(
        @Query("username") username: String
    ): Call<LibraryResponse>
}

interface BookRecommendService {
    @POST("api/recommendations")
    fun recommendBook(
        @Body request: RecommendRequest
    ): Call<RecommendResponse>
}