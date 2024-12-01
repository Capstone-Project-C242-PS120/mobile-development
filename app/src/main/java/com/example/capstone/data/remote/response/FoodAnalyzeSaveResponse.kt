package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodAnalyzeSaveResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)
