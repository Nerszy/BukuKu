package com.dicoding.bukuku

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bukuku.response.BooksItem
import com.dicoding.bukuku.tools.ApiConfig
import retrofit2.*

class DetailBookViewModel: ViewModel() {
    private val _book = MutableLiveData<BooksItem>()
    val book: LiveData<BooksItem> = _book

    fun getBookById(id: Int) {
        ApiConfig.getApiServices().getBookById(id)
            .enqueue(object: Callback<BooksItem>{
                override fun onResponse(call: Call<BooksItem>, response: Response<BooksItem>) {
                    if (response.isSuccessful) {
                        _book.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<BooksItem>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
}