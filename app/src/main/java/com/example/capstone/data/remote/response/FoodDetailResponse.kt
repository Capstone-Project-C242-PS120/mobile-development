package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodDetailResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

data class FoodDetailData(

	@field:SerializedName("fiber")
	val fiber: Any? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("vegetable")
	val vegetable: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("food_rate")
	val foodRate: Int? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null,

	@field:SerializedName("image_nutrition_url")
	val imageNutritionUrl: String? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("protein")
	val protein: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fat")
	val fat: Int? = null,

	@field:SerializedName("nutriscore")
	val nutriscore: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("natrium")
	val natrium: Any? = null,

	@field:SerializedName("sugar")
	val sugar: Int? = null
)
