package com.example.capstone.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.HistoryFoodResponse
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _historyResult = MutableLiveData<Result<HistoryFoodResponse>>()
    var historyResult: MutableLiveData<Result<HistoryFoodResponse>> = _historyResult

    fun foodHistory(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.foodHistory(token)
            _historyResult.value = result
            _isLoading.value = false
        }
    }
}