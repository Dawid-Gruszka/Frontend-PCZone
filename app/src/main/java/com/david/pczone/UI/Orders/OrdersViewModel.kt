import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.CartItems
import com.david.pczone.Models.User
import com.david.pczone.Models.OrderCreateResponse
import kotlinx.coroutines.launch

class OrdersViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItems>>()
    val cartItems: LiveData<List<CartItems>> get() = _cartItems

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _orderCreated = MutableLiveData<OrderCreateResponse?>()
    val orderCreated: LiveData<OrderCreateResponse?> get() = _orderCreated

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadCart(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCart("Bearer $token")
                if (response.isSuccessful) {
                    _cartItems.value = response.body()
                } else {
                    _errorMessage.value = "Error al obtener la cesta"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun loadUser(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentUser("Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let { _user.value = it }
                } else {
                    _errorMessage.value = "Error al obtener datos del usuario"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }

    fun crearPedido(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.createOrder("Bearer $token")
                if (response.isSuccessful) {
                    _orderCreated.value = response.body()
                } else {
                    _errorMessage.value = "Error al crear el pedido"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            }
        }
    }
}
