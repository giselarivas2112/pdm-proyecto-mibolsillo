package com.pdm0126.mibolsillo.view.screens.screenlogin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onesignal.OneSignal
import com.pdm0126.mibolsillo.data.model.Session
import com.pdm0126.mibolsillo.data.repositories.auth.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.auth.AuthRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.utils.isValidEmail
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
        _error.value = null
        when {

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

        }


        viewModelScope.launch {

            _loading.value = true

            repository.login(
                email,
                password
            )
                .onSuccess { session ->

                    sessionManager.saveToken(session.token)

                    val oneSignalId =
                        OneSignal.User.pushSubscription.id

                    android.util.Log.d(
                        "OneSignalTest",
                        "ID OBTENIDO: $oneSignalId"
                    )

                    if (!oneSignalId.isNullOrBlank()) {

                        repository.saveOneSignalId(
                            token = session.token,
                            oneSignalId = oneSignalId
                        )
                            .onSuccess {
                                android.util.Log.d(
                                    "OneSignalTest",
                                    "ONESIGNAL GUARDADO"
                                )
                            }
                            .onFailure {
                                android.util.Log.e(
                                    "OneSignalTest",
                                    "ERROR: ${it.message}"
                                )
                            }
                    }

                    _session.value =
                        session
                }
                .onFailure { error ->

                    sessionManager.clearToken()

                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }

            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}