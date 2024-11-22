package com.example.capstone.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FoodAnalyzeResponse(

	@field:SerializedName("data")
	val data: AnalyzeData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

@Parcelize
data class AnalyzeData(

	@field:SerializedName("carbo")
	val carbo: Int,

	@field:SerializedName("vitaminc")
	val vitaminc: Int,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("fat")
	val fat: Int,

	@field:SerializedName("rank")
	val rank: String,

	@field:SerializedName("calories")
	val calories: Int,

	@field:SerializedName("sugar")
	val sugar: Int
): Parcelable
