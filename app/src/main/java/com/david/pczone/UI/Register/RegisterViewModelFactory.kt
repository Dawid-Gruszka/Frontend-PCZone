package com.david.pczone.UI.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.david.pczone.API.Api

class RegisterViewModelFactory(
    private val api: Api
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
