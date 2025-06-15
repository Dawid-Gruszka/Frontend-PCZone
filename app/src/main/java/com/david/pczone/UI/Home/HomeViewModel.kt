package com.david.pczone.UI.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.Api
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Category
import com.david.pczone.Models.Product
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(private val userApi: Api) : ViewModel() {

    private val _categorias = MutableLiveData<List<Category>>()
    val categorias: LiveData<List<Category>> = _categorias

    private val _productos = MutableLiveData<List<Product>>()
    val productos: LiveData<List<Product>> = _productos

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            try {
                _loading.value = true

                val categoriasResponse = async { userApi.getCategorias() }
                val productosResponse = async { userApi.getProductos() }

                val categoriasResult = categoriasResponse.await()
                val productosResult = productosResponse.await()

                if (categoriasResult.isSuccessful) {
                    _categorias.value = categoriasResult.body().orEmpty()
                } else {
                    _error.value = "Error al obtener categorías"
                }

                if (productosResult.isSuccessful) {
                    _productos.value = productosResult.body()?.take(4).orEmpty()
                } else {
                    _error.value = "Error al obtener productos"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
