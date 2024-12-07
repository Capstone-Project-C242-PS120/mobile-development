package com.example.capstone.data.remote

import com.example.capstone.data.Result
import com.example.capstone.data.remote.response.DailyScanQuotaResponse
import com.example.capstone.data.remote.response.FoodAnalyzeResponse
import com.example.capstone.data.remote.response.FoodAnalyzeSaveResponse
import com.example.capstone.data.remote.response.FoodDetailResponse
import com.example.capstone.data.remote.response.GetProfileResponse
import com.example.capstone.data.remote.response.HistoryFoodResponse
import com.example.capstone.data.remote.response.LoginResponse
import com.example.capstone.data.remote.response.NewsResponse
import com.example.capstone.data.remote.response.NutritionSummaryResponse
import com.example.capstone.data.remote.response.RegisterResponse
import com.example.capstone.data.remote.response.SearchFoodResponse
import com.example.capstone.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService
){

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(email, password)
                return@withContext if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Email atau Password Salah:("
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }


    suspend fun register(name: String, email: String, password: String): Result<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(name, email, password)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error("${e.message}")
            }
        }
    }

    suspend fun getProfile(token: String): Result<GetProfileResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.getProfile(authToken)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error("${e.message}")
            }
        }
    }

    suspend fun analyzeFood(token: String, image: MultipartBody.Part): Result<FoodAnalyzeResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val token = "Bearer $token"
                val response = apiService.analyzeFood(token, image)
                if (response.statusCode == 201) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error("${e.message}")
            }
        }
    }

    suspend fun saveAnalyzeFood(
        token: String,
        image: MultipartBody.Part,
        name: RequestBody,
        nutriscore: RequestBody,
        grade: RequestBody,
        tags: RequestBody,
        calories: RequestBody,
        fat: RequestBody,
        sugar: RequestBody,
        fiber: RequestBody,
        protein: RequestBody,
        natrium: RequestBody,
        vegetable: RequestBody,
        foodRate: RequestBody?
    ): Result<FoodAnalyzeSaveResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.saveAnalyzeFood(
                    authToken,
                    image,
                    name,
                    nutriscore,
                    grade,
                    tags,
                    calories,
                    fat,
                    sugar,
                    fiber,
                    protein,
                    natrium,
                    vegetable,
                    foodRate
                )
                if (response.statusCode == 201) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun searchFood(
        token: String,
        page: Int?,
        limit: Int?,
        name: String?,
        tags: String?
    ): Result<SearchFoodResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.searchFood(authToken, page, limit, name, tags)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun detailFood(
        token: String,
        id: Int
    ): Result<FoodDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.detailFood(authToken, id)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun saveFood(
        token: String,
        foodId: Int,
        foodRate: Int?
    ): Result<FoodAnalyzeSaveResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.saveFood(authToken, foodId, foodRate)
                if (response.statusCode == 201) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun foodHistory(token: String): Result<HistoryFoodResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.foodHistory(authToken)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun getQuota(token: String): Result<DailyScanQuotaResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.getQuota(authToken)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun getSummary(token: String): Result<NutritionSummaryResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.getSummary(authToken)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    suspend fun getNews(token: String): Result<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = "Bearer $token"
                val response = apiService.getNews(authToken)
                if (response.statusCode == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access"
                    else -> "Error: ${e.message()}"
                }
                Result.Error(errorMessage)
            } catch (e: Exception) {
                Result.Error("Error: ${e.message ?: "Unknown error"}")
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(
            apiService: ApiService
        ): Repository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Repository(apiService)
        }.also { INSTANCE = it }
    }
}