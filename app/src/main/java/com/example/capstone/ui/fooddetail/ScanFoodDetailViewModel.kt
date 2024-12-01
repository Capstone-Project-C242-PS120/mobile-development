package com.example.capstone.ui.fooddetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.FoodAnalyzeSaveResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ScanFoodDetailViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _saveResult = MutableLiveData<Result<FoodAnalyzeSaveResponse>>()
    var saveResult: MutableLiveData<Result<FoodAnalyzeSaveResponse>> = _saveResult

    fun saveAnalyzeFood(
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
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.saveAnalyzeFood(
                token,
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
            _saveResult.value = result
            _isLoading.value = false
        }
    }
}
