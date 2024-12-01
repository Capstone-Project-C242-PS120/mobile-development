package com.example.capstone.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class FoodAnalyzeResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

@Parcelize
data class Data(

	@field:SerializedName("fiber")
	val fiber: Double? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("fat")
	val fat: Double? = null,

	@field:SerializedName("nutriscore")
	val nutriscore: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null,

	@field:SerializedName("natrium")
	val natrium: Double? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("vegetable")
		val vegetable: Double? = null,

	@field:SerializedName("sugar")
	val sugar: Double? = null
) : Parcelable
