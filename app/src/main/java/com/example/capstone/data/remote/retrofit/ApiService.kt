package com.example.capstone.data.remote.retrofit

import com.example.capstone.data.remote.response.GetProfileResponse
import com.example.capstone.data.remote.response.LoginResponse
import com.example.capstone.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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

}