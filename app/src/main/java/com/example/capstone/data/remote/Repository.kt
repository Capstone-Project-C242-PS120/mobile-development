package com.example.capstone.data.remote

import com.example.capstone.data.Result
import com.example.capstone.data.remote.response.LoginResponse
import com.example.capstone.data.remote.response.RegisterResponse
import com.example.capstone.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService
){

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(email, password)
                return@withContext if (response.status == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Invalid email or password"
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
                if (response.status == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error("${e.message}")
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