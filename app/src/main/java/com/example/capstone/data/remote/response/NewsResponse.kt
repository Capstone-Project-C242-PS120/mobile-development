package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("data")
	val data: NewsData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class NewsData(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String
)
