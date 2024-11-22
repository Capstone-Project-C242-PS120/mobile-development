package com.example.capstone.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.data.remote.Repository
import com.example.capstone.di.Injection
import com.example.capstone.ui.login.LoginViewModel
import com.example.capstone.ui.profile.ProfileViewModel
import com.example.capstone.ui.register.RegisterViewModel
import com.example.capstone.ui.scan.ScanViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.repository(context)
                )
            }.also { INSTANCE = it }
    }
}