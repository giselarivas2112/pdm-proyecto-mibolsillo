package com.pdm0126.mibolsillo.view.screens.ScreenRegisterFixed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.model.Category
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.fixedpayments.FixedPaymentApiRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FixedPaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val fixedPaymentRepository = FixedPaymentApiRepository(sessionManager)
    private val categoryRepository = CategoryApiRepository(sessionManager)

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _loadingCategories = MutableStateFlow(false)
    val loadingCategories: StateFlow<Boolean> = _loadingCategories

    private val _errorCategories = MutableStateFlow<String?>(null)
    val errorCategories: StateFlow<String?> = _errorCategories

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success

    init {
        loadCategories()
    }

    fun loadCategories(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) _refreshing.value = true
            else _loadingCategories.value = true
            _errorCategories.value = null

            val result = categoryRepository.getCategories()
            result.onSuccess { _categories.value = it }
            result.onFailure { _errorCategories.value = it.message }

            if (isRefresh) _refreshing.value = false
            else _loadingCategories.value = false
        }
    }

    fun createFixedPayment(
        nombre: String,
        categoriaId: String,
        monto: Double,
        diaVencimiento: Int,
        diasRecordatorio: Int
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val result = fixedPaymentRepository.createFixedPayment(
                nombre = nombre,
                categoriaId = categoriaId,
                monto = monto,
                diaVencimiento = diaVencimiento,
                diasRecordatorio = diasRecordatorio
            )
            result.onSuccess { _success.value = true }
            result.onFailure { _error.value = it.message }
            _loading.value = false
        }
    }

    fun resetState() {
        _success.value = false
        _error.value = null
    }
}