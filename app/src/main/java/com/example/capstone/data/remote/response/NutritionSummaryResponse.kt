package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class NutritionSummaryResponse(

	@field:SerializedName("data")
	val data: DataSummary,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class DataSummary(

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("sugar")
	val sugar: Double
)
