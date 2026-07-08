package com.pdm0126.mibolsillo.view.screens.screenregisterfixedpayment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.model.Category
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.fixedpayments.FixedPaymentApiRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterFixedPaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val fixedPaymentRepository = FixedPaymentApiRepository(sessionManager)
    private val categoryRepository = CategoryApiRepository(sessionManager)

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

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _success = MutableStateFlow(false)
    val success = _success.asStateFlow()


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
    fun resetError() {
        _error.value = null
    }
}