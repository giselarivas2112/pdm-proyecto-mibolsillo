package com.pdm0126.mibolsillo.screens.screenregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.model.User
import com.pdm0126.mibolsillo.data.repositories.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository: AuthRepository =
        AuthApiRepository()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun register(
        nombre: String,
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            repository.register(
                nombre,
                email,
                password
            )
                .onSuccess { user ->
                    _user.value = user
                }
                .onFailure { error ->
                    _error.value = error.message
                }

            _loading.value = false
        }
    }

    fun clearUser() {
        _user.value = null
    }
}