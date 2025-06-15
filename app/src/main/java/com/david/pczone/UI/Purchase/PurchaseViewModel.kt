package com.david.pczone.UI.Purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Order
import kotlinx.coroutines.launch

class PurchaseViewModel : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadOrders(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getOrdersByUser("Bearer $token")
                if (response.isSuccessful) {
                    _orders.value = response.body()
                } else {
                    _errorMessage.value = "Error al obtener los pedidos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }
}