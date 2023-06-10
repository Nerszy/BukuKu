package com.dicoding.bukuku.response

import com.google.gson.annotations.SerializedName

data class AuthorizationResponse(
	@field:SerializedName("err")
	val err: Boolean,

	@field:SerializedName("message")
	val message: String
)
