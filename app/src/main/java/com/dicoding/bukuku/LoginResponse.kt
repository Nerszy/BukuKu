package com.dicoding.bukuku

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: ArrayList<DataItem>,

    @field:SerializedName("status")
    val status: String
)

data class DataItem(

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)
