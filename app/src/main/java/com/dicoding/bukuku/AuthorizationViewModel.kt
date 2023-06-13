package com.dicoding.bukuku

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.bukuku.response.AuthorizationResponse
import com.dicoding.bukuku.tools.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationViewModel: ViewModel(){
    private val _responseAuth = MutableLiveData<AuthorizationResponse>()
    val responseAuth get() = _responseAuth
    val error = MutableLiveData<String>()

    fun setRegister(email: String, username: String, password: String){
        ApiConfig.getApiServices().register(username, email, password)
            .enqueue(object : Callback<AuthorizationResponse>{
                override fun onResponse(
                    call: Call<AuthorizationResponse>,
                    response: Response<AuthorizationResponse>
                ) {
                    _responseAuth.postValue(response.body())
                }

                override fun onFailure(call: Call<AuthorizationResponse>, t: Throwable) {
                    error.postValue(t.message)
                }

            })
    }

    fun setLogin(username: String, password: String, userPreference: UserPreference) {
        ApiConfig.getApiServices().login(username, password)
            .enqueue(object : Callback<AuthorizationResponse> {
                override fun onResponse(
                    call: Call<AuthorizationResponse>,
                    response: Response<AuthorizationResponse>
                ) {
                    _responseAuth.postValue(response.body())
                    if (response.isSuccessful){
                        viewModelScope.launch {
                            userPreference.saveUser(
                                response.body()?.username ?: "",
                                true
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<AuthorizationResponse>, t: Throwable) {
                    error.postValue(t.message)
                }

            })
    }
}