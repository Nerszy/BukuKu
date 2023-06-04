package com.dicoding.bukuku.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @field:SerializedName("books")
    val books: ArrayList<BooksItem>
)

data class BooksItem(

    @field:SerializedName("url_playbook")
    val urlPlaybook: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("isbn")
    val isbn: Int,

    @field:SerializedName("tags1")
    val tags1: String,

    @field:SerializedName("tags2")
    val tags2: String,

    @field:SerializedName("tags3")
    val tags3: String,

    @field:SerializedName("synopsis")
    val synopsis: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url_image")
    val urlImage: String,

    @field:SerializedName("idr")
    val idr: String,

    @field:SerializedName("avg_rating")
    val avgRating: String,

    @field:SerializedName("books_id")
    val booksId: Int,

    @field:SerializedName("_id")
    val id: String
)
