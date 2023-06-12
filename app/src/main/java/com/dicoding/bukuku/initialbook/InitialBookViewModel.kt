package com.dicoding.bukuku.initialbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bukuku.tools.ApiConfig
import com.dicoding.bukuku.response.BookResponse
import com.dicoding.bukuku.response.BooksItem
import retrofit2.*

class InitialBookViewModel : ViewModel() {
    private val _listBooksMap = HashMap<String, MutableLiveData<ArrayList<BooksItem>>>()
    val listBooksMap: Map<String, LiveData<ArrayList<BooksItem>>> = _listBooksMap
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()

    fun getBooksByGenre(genre: String) {
        loadingState.value = true
        val genreLiveData = MutableLiveData<ArrayList<BooksItem>>()
        _listBooksMap[genre] = genreLiveData

        ApiConfig.getApiServices().sortByGenre(genre)
            .enqueue(object : Callback<BookResponse> {
                override fun onResponse(
                    call: Call<BookResponse>,
                    response: Response<BookResponse>
                ) {
                    if (response.isSuccessful) {
                        genreLiveData.postValue(response.body()?.books)
                        loadingState.value = false
                    }
                }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    loadingState.value = false
                    errorState.value = true
                }
            })
    }
}
