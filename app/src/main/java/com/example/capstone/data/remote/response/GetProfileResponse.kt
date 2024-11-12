package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("data")
	val data: ProfileData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class ProfileData(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String
)
