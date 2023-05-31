package com.dicoding.bukuku

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*

class SearchViewModel: ViewModel() {
    private val _listBook = MutableLiveData<ArrayList<BooksItem>>()
    val listBook: LiveData<ArrayList<BooksItem>> = _listBook

    fun setBook() {
        ApiConfig.getApiServices().getBooks()
            .enqueue(object : Callback<BookResponseItem>{
                override fun onResponse(
                    call: Call<BookResponseItem>,
                    response: Response<BookResponseItem>
                ) {
                    if (response.isSuccessful) {
                        _listBook.postValue(response.body()?.books)
                    }
                }

                override fun onFailure(call: Call<BookResponseItem>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
}