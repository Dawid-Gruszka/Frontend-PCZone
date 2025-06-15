package com.david.pczone.UI.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pczone.API.RetrofitInstance
import com.david.pczone.Models.User
import kotlinx.coroutines.launch

class ProfilePageViewModel : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentUser("Bearer $token")
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                } else {
                    _error.postValue("Usuario no autorizado")
                    _user.postValue(null)
                }
            } catch (e: Exception) {
                _error.postValue("Error al obtener usuario: ${e.message}")
                _user.postValue(null)
            }
        }
    }
}

