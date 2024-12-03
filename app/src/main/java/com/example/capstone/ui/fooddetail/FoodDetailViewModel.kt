package com.example.capstone.ui.fooddetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.FoodAnalyzeSaveResponse
import com.example.capstone.data.remote.response.FoodDetailResponse
import kotlinx.coroutines.launch

class FoodDetailViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _detailResult = MutableLiveData<Result<FoodDetailResponse>>()
    var detailResult: MutableLiveData<Result<FoodDetailResponse>> = _detailResult

    private val _saveResult = MutableLiveData<Result<FoodAnalyzeSaveResponse>>()
    var saveResult: MutableLiveData<Result<FoodAnalyzeSaveResponse>> = _saveResult

    fun detailFood(
        token: String,
        id: Int
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.detailFood(token, id)
            _detailResult.value = result
            _isLoading.value = false
        }
    }

    fun saveFood(
        token: String,
        foodId: Int,
        foodRate: Int?
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.saveFood(token, foodId, foodRate)
            _saveResult.value = result
            _isLoading.value = false
        }
    }
}