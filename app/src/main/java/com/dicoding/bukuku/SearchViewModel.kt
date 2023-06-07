package com.dicoding.bukuku

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.bukuku.response.BookResponse
import com.dicoding.bukuku.response.BooksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(bookRepository: BookRepository) : ViewModel() {
    val listBook: LiveData<PagingData<BooksItem>> =
        bookRepository.getBooks().cachedIn(viewModelScope)
}

class SearchViewModelFactory(private val bookRepository: BookRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}


