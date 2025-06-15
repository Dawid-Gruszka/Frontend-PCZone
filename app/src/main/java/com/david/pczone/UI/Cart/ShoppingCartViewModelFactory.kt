package com.david.pczone.UI.Cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.david.pczone.API.Api

class ShoppingCartViewModelFactory(private val userApi: Api) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingCartViewModel(userApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
