package com.example.capstone.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.NewsResponse
import com.example.capstone.data.remote.response.NutritionSummaryResponse
import com.example.capstone.data.remote.response.RecomendationResponse
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _summaryResult = MutableLiveData<Result<NutritionSummaryResponse>>()
    var summaryResult: MutableLiveData<Result<NutritionSummaryResponse>> = _summaryResult

    private val _newsResult = MutableLiveData<Result<NewsResponse>>()
    var newsResult: MutableLiveData<Result<NewsResponse>> = _newsResult

    private val _recomendationResult = MutableLiveData<Result<RecomendationResponse>>()
    var recomendationResult: MutableLiveData<Result<RecomendationResponse>> = _recomendationResult
    fun getSummary(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getSummary(token)
            _summaryResult.value = result
            _isLoading.value = false
        }
    }

    fun getNews(token: String) {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getNews(token)
            _newsResult.value = result
            _isLoading.value = false
        }
    }

    fun getRecomendation(token: String) {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getRecomendation(token)
            _recomendationResult.value = result
            _isLoading.value = false
        }
    }
}