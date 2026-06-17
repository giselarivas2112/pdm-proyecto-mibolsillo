package com.pdm0126.mibolsillo.screens.screenlogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.model.Session
import com.pdm0126.mibolsillo.data.repositories.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository: AuthRepository =
        AuthApiRepository()

    private val _session =
        MutableStateFlow<Session?>(null)

    val session =
        _session.asStateFlow()

    private val _loading =
        MutableStateFlow(false)

    val loading =
        _loading.asStateFlow()

    private val _error =
        MutableStateFlow<String?>(null)

    val error =
        _error.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            _loading.value = true
            _error.value = null

            repository.login(
                email,
                password
            )
                .onSuccess { session ->

                    _session.value =
                        session
                }
                .onFailure { error ->

                    _error.value =
                        error.message
                }

            _loading.value = false
        }
    }


}