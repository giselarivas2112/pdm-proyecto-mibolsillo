package com.pdm0126.mibolsillo.view.screens.screenlogin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.model.Session
import com.pdm0126.mibolsillo.data.repositories.auth.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.auth.AuthRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthRepository =
        AuthApiRepository()

    private val sessionManager = SessionManager(application)

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

                    sessionManager.saveToken(session.token)

                    _session.value =
                        session
                }
                .onFailure { error ->
                    sessionManager.clearToken()
                    _error.value =
                        error.message
                }

            _loading.value = false
        }
    }
}