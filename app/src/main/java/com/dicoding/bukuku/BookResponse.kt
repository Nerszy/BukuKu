package com.dicoding.bukuku

import com.google.gson.annotations.SerializedName

data class BookResponse(

	@field:SerializedName("BookResponse")
	val bookResponse: ArrayList<BookResponseItem>
)

data class BooksItem(

	@field:SerializedName("nilai rupiah saat ini dari dollar")
	val nilaiRupiahSaatIniDariDollar: String,

	@field:SerializedName("url_playbook")
	val urlPlaybook: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("books_id")
	val booksId: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("synopsis")
	val synopsis: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url_image")
	val urlImage: String
)

data class BookResponseItem(

	@field:SerializedName("books")
	val books: ArrayList<BooksItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
