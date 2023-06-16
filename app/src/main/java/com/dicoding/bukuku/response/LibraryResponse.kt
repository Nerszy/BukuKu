package com.dicoding.bukuku.response

import com.google.gson.annotations.SerializedName

data class LibraryResponse(
	@field:SerializedName("library")
	val library: ArrayList<LibraryItem>
)

data class LibraryItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("book")
	val book: BooksItem,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: String
)

data class PostLibraryResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("book")
	val book: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: String
)

