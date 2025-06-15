package com.david.pczone.UI.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.Api
import com.david.pczone.Models.UserCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val api: Api) : ViewModel() {

    private val _registerResult = MutableStateFlow<String?>(null)
    val registerResult: StateFlow<String?> = _registerResult

    fun register(userCreate: UserCreate) {
        viewModelScope.launch {
            try {
                val response = api.register(userCreate)
                if (response.isSuccessful) {
                    _registerResult.value = "Registro Completado"
                } else if (response.code() == 400) {
                    _registerResult.value = "Error: Usuario ya existe"
                }
                else {
                    _registerResult.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _registerResult.value = "Excepción: ${e.localizedMessage ?: e.message}"
            }
        }
    }
}
