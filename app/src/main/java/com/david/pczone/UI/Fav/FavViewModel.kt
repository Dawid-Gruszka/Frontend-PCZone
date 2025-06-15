package com.david.pczone.UI.Fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Product
import kotlinx.coroutines.launch

class FavViewModel : ViewModel() {

    private val _favItems = MutableLiveData<List<Product>>()
    val favItems: LiveData<List<Product>> get() = _favItems

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _infoMessage = MutableLiveData<String?>()
    val infoMessage: LiveData<String?> get() = _infoMessage

    fun loadFav(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getFav("Bearer $token")
                if (response.isSuccessful) {
                    _favItems.value = response.body()
                } else {
                    _errorMessage.value = "Error al obtener favoritos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun deleteFav(token: String, productId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteFav("Bearer $token", productId)
                if (response.isSuccessful) {
                    _infoMessage.value = "Producto eliminado de favoritos"
                    loadFav(token)
                } else {
                    _errorMessage.value = "Error al eliminar producto de favoritos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun addToCart(token: String, productId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addToCart("Bearer $token", productId, 1)
                if (response.isSuccessful) {
                    _infoMessage.value = "Producto añadido al carrito"
                    loadFav(token)
                } else {
                    _errorMessage.value = "Error al añadir al carrito"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun clearMessages() {
        _errorMessage.value = null
        _infoMessage.value = null
    }
}
