package com.example.capstone.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.NutritionSummaryResponse
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _summaryResult = MutableLiveData<Result<NutritionSummaryResponse>>()
    var summaryResult: MutableLiveData<Result<NutritionSummaryResponse>> = _summaryResult

    fun getSummary(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getSummary(token)
            _summaryResult.value = result
            _isLoading.value = false
        }
    }
}