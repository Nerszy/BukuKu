package com.dicoding.bukuku

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.bukuku.response.BooksItem

class DiscoverViewModel(bookRepository: BookRepository) : ViewModel() {
    val listBook: LiveData<PagingData<BooksItem>> =
        bookRepository.getBooks().cachedIn(viewModelScope)
}

class DiscoverViewModelFactory(private val bookRepository: BookRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverViewModel::class.java)) {
            return DiscoverViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}


