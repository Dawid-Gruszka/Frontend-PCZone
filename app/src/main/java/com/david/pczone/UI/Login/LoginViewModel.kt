package com.david.pczone.UI.Login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.TokenManager
import com.david.pczone.API.Api
import com.david.pczone.Models.Token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val api: Api) : ViewModel() {

    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> = _loginResult

    fun login(username: String, password: String, context: Context) {
        viewModelScope.launch {
            try {
                val response: Response<Token> = api.login(username, password)
                if (response.isSuccessful) {
                    val token = response.body()
                    if (token != null) {
                        TokenManager.saveToken(context, token.access_token)
                        val tokenBearer = "Bearer ${token.access_token}"
                        val userResponse = api.getCurrentUser(tokenBearer)
                        if (userResponse.isSuccessful) {
                            _loginResult.value = "SUCCESS"
                        } else {
                            _loginResult.value = "Error al obtener el usuario"
                        }
                    } else {
                        _loginResult.value = "Error: token vacío"
                    }
                } else {
                    _loginResult.value = "Error: Usuario o contraseña incorrectos"
                }
            } catch (e: Exception) {
                _loginResult.value = "Excepción: ${e.localizedMessage ?: e.message}"
            }
        }
    }
}