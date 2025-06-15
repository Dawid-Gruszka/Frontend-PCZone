package com.david.pczone.UI.Menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Category
import com.david.pczone.Models.Product
import kotlinx.coroutines.launch

class ShopMenuViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _filteredProducts = MutableLiveData<List<Product>>()
    val filteredProducts: LiveData<List<Product>> get() = _filteredProducts

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategorias()
                if (response.isSuccessful) {
                    _categories.value = response.body()
                } else {
                    _errorMessage.value = "Error al obtener categorias"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun loadAllProducts() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProductos()
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    _products.value = list
                    _filteredProducts.value = list
                } else {
                    _errorMessage.value = "Error al obtener productos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun filterProducts(query: String) {
        val currentProducts = _products.value ?: emptyList()
        _filteredProducts.value = if (query.isBlank()) {
            currentProducts
        } else {
            currentProducts.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}
