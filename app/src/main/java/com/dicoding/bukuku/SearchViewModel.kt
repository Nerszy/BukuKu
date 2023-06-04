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

    fun setBook() {
        ApiConfig.getApiServices().getBooks()
            .enqueue(object : Callback<BookResponse> {
                override fun onResponse(
                    call: Call<BookResponse>,
                    response: Response<BookResponse>
                ) {
                    if (response.isSuccessful) {
                        _listBook.postValue(response.body()?.books)
                    }
                }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
}