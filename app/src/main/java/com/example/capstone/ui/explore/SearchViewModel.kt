package com.example.capstone.ui.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.SearchFoodResponse
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _searchResult = MutableLiveData<Result<SearchFoodResponse>>()
    var searchResult: MutableLiveData<Result<SearchFoodResponse>> = _searchResult

    fun searchFood(token: String, page: Int?, limit: Int?, name: String?, tags: String?) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.searchFood(token, page, limit, name, tags)
            _searchResult.value = if (result is Result.Success && result.data.data.isEmpty()) {
                Result.Error("Maaf kami tidak mempunyai data makanan yang kamu cari:(")
            } else {
                result
            }
            _isLoading.value = false
        }
    }
}