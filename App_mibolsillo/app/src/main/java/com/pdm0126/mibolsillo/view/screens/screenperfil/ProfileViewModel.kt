package com.pdm0126.mibolsillo.view.screens.screenperfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    fun logout() {

        viewModelScope.launch {

            sessionManager.clearToken()

        }
    }
}