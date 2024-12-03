package com.example.capstone.data.remote.retrofit

import com.example.capstone.data.remote.response.DailyScanQuotaResponse
import com.example.capstone.data.remote.response.FoodAnalyzeResponse
import com.example.capstone.data.remote.response.FoodAnalyzeSaveResponse
import com.example.capstone.data.remote.response.FoodDetailResponse
import com.example.capstone.data.remote.response.GetProfileResponse
import com.example.capstone.data.remote.response.HistoryFoodResponse
import com.example.capstone.data.remote.response.LoginResponse
import com.example.capstone.data.remote.response.NutritionSummaryResponse
import com.example.capstone.data.remote.response.RegisterResponse
import com.example.capstone.data.remote.response.SearchFoodResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("api/user/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("api/user/getUser")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): GetProfileResponse

    @Multipart
    @POST("api/food/analyze")
    suspend fun analyzeFood(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): FoodAnalyzeResponse

    @Multipart
    @POST("api/food/analyze/save")
    suspend fun saveAnalyzeFood(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("nutriscore") nutriscore: RequestBody,
        @Part("grade") grade: RequestBody,
        @Part("tags") tags: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part("fat") fat: RequestBody,
        @Part("sugar") sugar: RequestBody,
        @Part("fiber") fiber: RequestBody,
        @Part("protein") protein: RequestBody,
        @Part("natrium") natrium: RequestBody,
        @Part("vegetable") vegetable: RequestBody,
        @Part("food_rate") foodRate: RequestBody?
    ): FoodAnalyzeSaveResponse

    @GET("api/food/filter")
    suspend fun searchFood(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("name") name: String? = null,
        @Query("tags") tags: String? = null
    ): SearchFoodResponse

    @GET("api/food/detail")
    suspend fun detailFood(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): FoodDetailResponse

    @FormUrlEncoded
    @POST("api/food/save")
    suspend fun saveFood(
        @Header("Authorization") token: String,
        @Field("food_id") foodId: Int,
        @Field("food_rate") foodRate: Int?
    ): FoodAnalyzeSaveResponse

    @GET("api/user/history")
    suspend fun foodHistory(
        @Header("Authorization") token: String
    ): HistoryFoodResponse

    @GET("api/user/scan")
    suspend fun getQuota(
        @Header("Authorization") token: String
    ): DailyScanQuotaResponse

    @GET("api/user/summary")
    suspend fun getSummary(
        @Header("Authorization") token: String
    ): NutritionSummaryResponse
}