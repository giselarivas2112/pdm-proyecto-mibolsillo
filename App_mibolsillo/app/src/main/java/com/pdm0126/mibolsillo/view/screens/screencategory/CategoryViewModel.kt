package com.pdm0126.mibolsillo.view.screens.screencategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: CategoryRepository = CategoryApiRepository(sessionManager)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    fun createCategory(nombre: String, icono: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.createCategory(nombre, icono)
                .onSuccess { _success.value = true }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }

    fun resetState() {
        _success.value = false
        _error.value = null
    }
}