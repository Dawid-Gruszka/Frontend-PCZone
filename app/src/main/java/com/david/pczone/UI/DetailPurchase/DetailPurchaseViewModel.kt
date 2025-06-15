package com.david.pczone.UI.DetailPurchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.Order
import com.david.pczone.Models.OrderItem
import com.david.pczone.Models.Product
import kotlinx.coroutines.launch

class DetailPurchaseViewModel : ViewModel() {

    private val _order = MutableLiveData<Order?>()
    val order: MutableLiveData<Order?> = _order

    private val _itemsConProducto = MutableLiveData<List<Pair<OrderItem, Product>>>()
    val itemsConProducto: LiveData<List<Pair<OrderItem, Product>>> = _itemsConProducto

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _products = MutableLiveData<List<Product>>()

    fun loadOrderDetail(orderId: Int, token: String) {
        viewModelScope.launch {
            try {
                val productsResponse = RetrofitInstance.api.getProductos()
                val ordersResponse = RetrofitInstance.api.getOrdersByUser("Bearer $token")

                if (productsResponse.isSuccessful && ordersResponse.isSuccessful) {
                    val productos = productsResponse.body() ?: emptyList()
                    val pedidos = ordersResponse.body() ?: emptyList()

                    _products.value = productos

                    val pedido = pedidos.find { it.id == orderId }
                    if (pedido != null) {
                        _order.value = pedido
                        val combinados = pedido.items.mapNotNull { item ->
                            val producto = productos.find { it.id == item.product_id }
                            producto?.let { Pair(item, it) }
                        }
                        _itemsConProducto.value = combinados
                    } else {
                        _errorMessage.value = "Pedido no encontrado"
                    }

                } else {
                    _errorMessage.value = "Error al cargar pedidos o productos"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }
}
