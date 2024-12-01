package com.example.capstone.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FoodAnalyzeResponse(

	@field:SerializedName("data")
	val data: DataAnalyze,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

@Parcelize
data class DataAnalyze(

	@field:SerializedName("fiber")
	val fiber: Double,

	@field:SerializedName("grade")
	val grade: Char,

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("fat")
	val fat: Double,

	@field:SerializedName("nutriscore")
	val nutriscore: Double,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("natrium")
	val natrium: Double,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("vegetable")
	val vegetable: Double,

	@field:SerializedName("sugar")
	val sugar: Double
): Parcelable
