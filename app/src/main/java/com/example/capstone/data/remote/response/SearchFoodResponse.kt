package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchFoodResponse(

	@field:SerializedName("totalData")
	val totalData: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("limit")
	val limit: String,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("page")
	val page: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class DataItem(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("grade")
	val grade: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("tags")
	val tags: List<String>
)
