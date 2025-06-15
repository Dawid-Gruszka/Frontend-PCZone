package com.david.pczone.UI.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.david.pczone.API.Api

class LoginViewModelFactory(private val api: Api) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}