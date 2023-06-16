package com.dicoding.bukuku

import com.google.gson.annotations.SerializedName

data class RecommendResponse(
	@field:SerializedName("recommendations")
	val recommendations: ArrayList<Int>
)
