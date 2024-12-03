package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class DailyScanQuotaResponse(

	@field:SerializedName("data")
	val data: DataQuota,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class DataQuota(

	@field:SerializedName("scan_quota")
	val scanQuota: String
)
