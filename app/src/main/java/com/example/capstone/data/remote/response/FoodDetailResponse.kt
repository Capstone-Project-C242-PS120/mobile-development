package com.example.capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodDetailResponse(

	@field:SerializedName("data")
	val data: DetailFood,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class DetailFood(

	@field:SerializedName("fiber")
	val fiber: Double,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("calories")
	val calories: Double,

	@field:SerializedName("vegetable")
	val vegetable: Double,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("food_rate")
	val foodRate: Int,

	@field:SerializedName("tags")
	val tags: List<String>,

	@field:SerializedName("image_nutrition_url")
	val imageNutritionUrl: String,

	@field:SerializedName("grade")
	val grade: Char,

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("fat")
	val fat: Double,

	@field:SerializedName("nutriscore")
	val nutriscore: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("natrium")
	val natrium: Double,

	@field:SerializedName("sugar")
	val sugar: Double
)
