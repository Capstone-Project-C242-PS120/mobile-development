package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryFoodResponse(

	@field:SerializedName("data")
	val data: List<HistoryItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class HistoryItem(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("grade")
	val grade: Char,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("tags")
	val tags: List<String>
)
