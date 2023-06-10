package com.dicoding.bukuku

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bukuku.response.BookResponse
import com.dicoding.bukuku.response.BooksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _listBook = MutableLiveData<ArrayList<BooksItem>>()
    val listBook: LiveData<ArrayList<BooksItem>> = _listBook

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _errorState = MutableLiveData<Int>()
    val errorState: LiveData<Int> = _errorState

    fun getSearchBook(query: String) {
        _loadingState.value = true

        ApiConfig.getApiServices().searchBook(query)
            .enqueue(object : Callback<BookResponse> {
                override fun onResponse(
                    call: Call<BookResponse>,
                    response: Response<BookResponse>
                ) {
                    _loadingState.value = false

                    if (response.isSuccessful) {
                        _listBook.postValue(response.body()?.books)
                    } else {
                        _errorState.value = response.code()
                    }
                }

                override fun onFailure(
                    call: Call<BookResponse>,
                    t: Throwable
                ) {
                    _loadingState.value = false
                    _errorState.value = -1 // Jika request gagal, berikan nilai kode -1
                    Log.d("Search", "Request failed: ${t.message}")
                }
            })
    }
}
