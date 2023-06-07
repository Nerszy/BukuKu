package com.dicoding.bukuku

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.bukuku.response.BooksItem

class BookPagingSource(private val apiService: BookApiService) : PagingSource<Int, BooksItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BooksItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllBooks(position, params.loadSize)
            LoadResult.Page(
                data = responseData.books,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.books.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BooksItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}