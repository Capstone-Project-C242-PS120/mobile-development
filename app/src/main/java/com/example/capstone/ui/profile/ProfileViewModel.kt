package com.example.capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.data.Result
import com.example.capstone.data.remote.Repository
import com.example.capstone.data.remote.response.GetProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profileResult = MutableLiveData<Result<GetProfileResponse>>()
    val profileResult: MutableLiveData<Result<GetProfileResponse>> = _profileResult

    fun getProfile(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getProfile(token)
            _profileResult.value = result
            _isLoading.value = false
        }
    }
}