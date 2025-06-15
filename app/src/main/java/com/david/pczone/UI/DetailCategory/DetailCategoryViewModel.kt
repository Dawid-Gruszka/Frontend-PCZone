package com.david.pczone.UI.DetailCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Product
import kotlinx.coroutines.launch

class DetailCategoryViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _categoryName = MutableLiveData<String>()
    val categoryName: LiveData<String> get() = _categoryName

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadProducts(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProductos()
                if (response.isSuccessful) {
                    val allProducts = response.body()
                    val filtered = allProducts?.filter { it.category.id == categoryId } ?: emptyList()
                    _products.value = filtered
                } else {
                    _errorMessage.value = "Error al obtener productos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun loadCategoryName(categoryId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategorias()
                if (response.isSuccessful) {
                    val categories = response.body()
                    val category = categories?.find { it.id == categoryId }
                    _categoryName.value = category?.name ?: "Categoría no encontrada"
                } else {
                    _errorMessage.value = "Error al obtener categorías"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
