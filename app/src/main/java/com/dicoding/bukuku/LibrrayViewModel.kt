package com.dicoding.bukuku

import android.widget.Toast
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bukuku.response.LibraryItem
import com.dicoding.bukuku.response.LibraryResponse
import com.dicoding.bukuku.response.PostLibraryResponse
import com.dicoding.bukuku.tools.ApiConfig
import retrofit2.*

class LibrrayViewModel: ViewModel() {
    private val _library = MutableLiveData<ArrayList<LibraryItem>>()
    val library: LiveData<ArrayList<LibraryItem>> = _library

    val postLibrary = MutableLiveData<PostLibraryResponse>()

    fun getLibrary(username: String) {
        ApiConfig.getApiServices().getLibrary(username).enqueue(
            object : Callback<LibraryResponse> {
                override fun onResponse(
                    call: Call<LibraryResponse>,
                    response: Response<LibraryResponse>
                ) {
                    if (response.isSuccessful) {
                        _library.postValue(response.body()?.library)
                    }
                }

                override fun onFailure(call: Call<LibraryResponse>, t: Throwable) {
                    // Do nothing
                }
            }
        )
    }

    fun postLibrary(username: String, books_id: String){
        ApiConfig.getApiServices().postLibrary(username, books_id)
            .enqueue(object : Callback<PostLibraryResponse> {
                override fun onResponse(
                    call: Call<PostLibraryResponse>,
                    response: Response<PostLibraryResponse>
                ) {
                    postLibrary.postValue(response.body())
                }

                override fun onFailure(call: Call<PostLibraryResponse>, t: Throwable) {
                    Toast.makeText(null, "Gagal menambahkan buku ke library", Toast.LENGTH_SHORT).show()
                }

            })
    }
}