package com.david.pczone.UI.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.david.pczone.API.Api

class HomeViewModelFactory(private val userApi: Api) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(userApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
