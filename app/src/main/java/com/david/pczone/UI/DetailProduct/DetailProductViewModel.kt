package com.david.pczone.UI.DetailProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Product
import kotlinx.coroutines.launch

class DetailProductViewModel : ViewModel() {

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> get() = _product

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    fun loadProduct(productId: Int, token: String?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProducto(productId)
                if (response.isSuccessful) {
                    val prod = response.body()
                    _product.value = prod

                    if (token != null && prod != null) {
                        val favResponse = RetrofitInstance.api.getFav("Bearer $token")
                        if (favResponse.isSuccessful) {
                            val favList = favResponse.body()
                            _isFavorite.value = favList?.any { it.id == prod.id } == true
                        } else {
                            _message.value = "No se pudieron cargar los favoritos"
                        }
                    } else {
                        _isFavorite.value = false
                    }
                } else {
                    _message.value = "Error al obtener productos"
                }
            } catch (e: Exception) {
                _message.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun addToCart(token: String, productId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addToCart("Bearer $token", productId)
                if (response.isSuccessful) {
                    _message.value = "Producto agregado a la cesta"
                } else {
                    _message.value = "Error al agregar el producto a la cesta"
                }
            } catch (e: Exception) {
                _message.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun toggleFavorite(token: String, productId: Int, currentlyFavorite: Boolean) {
        viewModelScope.launch {
            try {
                val response = if (currentlyFavorite) {
                    RetrofitInstance.api.deleteFav("Bearer $token", productId)
                } else {
                    RetrofitInstance.api.addToFav("Bearer $token", productId)
                }

                if (response.isSuccessful) {
                    _isFavorite.value = !currentlyFavorite
                    _message.value = if (currentlyFavorite) {
                        "Producto eliminado de favoritos"
                    } else {
                        "Producto agregado a favoritos"
                    }
                } else {
                    _message.value = if (currentlyFavorite) {
                        "Error al eliminar el producto de favoritos"
                    } else {
                        "Error al agregar el producto a favoritos"
                    }
                }
            } catch (e: Exception) {
                _message.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}
