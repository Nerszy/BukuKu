package com.dicoding.bukuku

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.bukuku.response.BookPagingResponse
import com.dicoding.bukuku.response.BooksItem

class BookRepository(private val apiService: BookApiService) {
    fun getBooks(): LiveData<PagingData<BooksItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                BookPagingSource(apiService)
            }
        ).liveData
    }
}