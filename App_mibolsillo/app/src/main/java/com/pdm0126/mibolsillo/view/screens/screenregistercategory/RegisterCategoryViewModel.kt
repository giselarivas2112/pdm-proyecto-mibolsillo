package com.pdm0126.mibolsillo.view.screens.screenregistercategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.data.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: CategoryRepository = CategoryApiRepository(sessionManager)

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) _refreshing.value = true
            else _loading.value = true

            repository.getCategories()
                .onSuccess { _categories.value = it }
                .onFailure { e -> _error.value = e.message }

            if (isRefresh) _refreshing.value = false
            else _loading.value = false
        }
    }

    fun createCategory(nombre: String, icono: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.createCategory(nombre, icono)
                .onSuccess {
                    _success.value = true
                    getCategories()
                }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }

    fun resetState() {
        _success.value = false
        _error.value = null
    }
}