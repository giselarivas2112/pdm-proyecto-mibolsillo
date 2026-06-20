package com.pdm0126.mibolsillo.view.screens.screenexpense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.api.expense.AlertaDto
import com.pdm0126.mibolsillo.model.Category
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseApiRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: ExpenseRepository = ExpenseApiRepository(sessionManager)
    private val categoryRepository: CategoryRepository = CategoryApiRepository(sessionManager)

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _loadingCategories = MutableStateFlow(false)
    val loadingCategories = _loadingCategories.asStateFlow()

    private val _errorCategories = MutableStateFlow<String?>(null)
    val errorCategories = _errorCategories.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    private val _alerta = MutableStateFlow<AlertaDto?>(null)
    val alerta = _alerta.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _loadingCategories.value = true
            _errorCategories.value = null

            categoryRepository.getCategories()
                .onSuccess { _categories.value = it }
                .onFailure { e -> _errorCategories.value = e.message }

            _loadingCategories.value = false
        }
    }

    fun createExpense(categoriaId: String?, monto: Double, fecha: String, descripcion: String?) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.createExpense(categoriaId, monto, fecha, descripcion)
                .onSuccess { resultado ->
                    _success.value = true
                    _alerta.value = resultado.alerta
                }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
        }
    }

    fun resetState() {
        _success.value = false
        _error.value = null
        _alerta.value = null
    }
}