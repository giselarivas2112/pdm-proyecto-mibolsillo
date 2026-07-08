package com.pdm0126.mibolsillo.view.screens.screensignup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.model.User
import com.pdm0126.mibolsillo.data.repositories.auth.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.auth.AuthRepository
import com.pdm0126.mibolsillo.utils.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

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
        password: String,
        confirmPassword: String
    ) {

        _error.value = null

        when {

            nombre.isBlank() -> {
                _error.value = "El nombre es obligatorio"
                return
            }


            email.isBlank() -> {
                _error.value = "El correo es obligatorio"
                return
            }


            !isValidEmail(email) -> {
                _error.value = "Ingresa un correo válido"
                return
            }


            password.isBlank() -> {
                _error.value = "La contraseña es obligatoria"
                return
            }


            password != confirmPassword -> {
                _error.value = "Las contraseñas no coinciden"
                return
            }

        }


        viewModelScope.launch {

            _loading.value = true


            repository.register(
                nombre,
                email,
                password
            )
                .onSuccess { user ->
                    _error.value = null
                    _user.value = user

                }
                .onFailure { error ->

                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }


            _loading.value = false

        }

    }


    fun clearUser() {
        _user.value = null
    }

    fun clearError() {
        _error.value = null
    }
}