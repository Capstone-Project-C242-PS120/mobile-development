package com.example.capstone.di

import android.content.Context
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.retrofit.ApiConfig

object Injection {
    fun repository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}