package com.david.pczone.UI.Cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.Api
import com.david.pczone.Models.CartItems
import kotlinx.coroutines.launch

class ShoppingCartViewModel(private val userApi: Api) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItems>?>()
    val cartItems: LiveData<List<CartItems>?> = _cartItems

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _isCartEmpty = MutableLiveData<Boolean>()
    val isCartEmpty: LiveData<Boolean> = _isCartEmpty

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadCart(token: String) {
        viewModelScope.launch {
            try {
                val response = userApi.getCart("Bearer $token")
                if (response.isSuccessful) {
                    val items = response.body()
                    if (!items.isNullOrEmpty()) {
                        _cartItems.value = items
                        _isCartEmpty.value = false
                        val total = items.sumOf { it.product.price * it.quantity }
                        _totalPrice.value = total
                    } else {
                        _cartItems.value = emptyList()
                        _isCartEmpty.value = true
                        _totalPrice.value = 0
                    }
                } else {
                    _error.value = "Error al obtener la cesta"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun updateCart(token: String, productId: Int, quantityDelta: Int) {
        viewModelScope.launch {
            try {
                val response = userApi.updateCart("Bearer $token", productId, quantityDelta)
                if (response.isSuccessful) {
                    loadCart(token)
                } else {
                    _error.value = "Error al cambiar cantidad"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            }
        }
    }
}
