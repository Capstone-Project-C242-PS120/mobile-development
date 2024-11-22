package com.example.capstone.ui.scan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.FoodAnalyzeResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ScanViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _analyzeResult = MutableLiveData<Result<FoodAnalyzeResponse>>()
    var analyzeResult: MutableLiveData<Result<FoodAnalyzeResponse>> = _analyzeResult

    fun analyzeFood(
        token: String,
        image: MultipartBody.Part
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.analyzeFood(token, image)
            _analyzeResult.value = result
            _isLoading.value = false
        }
    }
}