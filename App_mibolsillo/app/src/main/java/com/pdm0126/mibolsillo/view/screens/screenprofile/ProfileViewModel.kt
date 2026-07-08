package com.pdm0126.mibolsillo.view.screens.screenprofile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.auth.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.auth.AuthRepository
import com.pdm0126.mibolsillo.data.repositories.budget.BudgetApiRepository
import com.pdm0126.mibolsillo.data.repositories.budget.BudgetRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseApiRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val authRepository: AuthRepository = AuthApiRepository()
    private val categoryRepository: CategoryRepository = CategoryApiRepository(sessionManager)
    private val expenseRepository: ExpenseRepository = ExpenseApiRepository(sessionManager)
    private val budgetRepository: BudgetRepository = BudgetApiRepository(sessionManager)

    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario = _nombreUsuario.asStateFlow()

    private val _totalCategorias = MutableStateFlow(0)
    val totalCategorias = _totalCategorias.asStateFlow()

    private val _totalGastos = MutableStateFlow(0)
    val totalGastos = _totalGastos.asStateFlow()

    private val _totalPresupuestos = MutableStateFlow(0)
    val totalPresupuestos = _totalPresupuestos.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    fun loadProfileData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) _refreshing.value = true
            else _loading.value = true
            _error.value = null

            val token = sessionManager.getToken() ?: run {
                _error.value = "Sesión no encontrada"
                _loading.value = false
                _refreshing.value = false
                return@launch
            }

            authRepository.getProfile(token)
                .onSuccess { _nombreUsuario.value = it.nombre }
                .onFailure { error ->
                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }

            categoryRepository.getCategories()
                .onSuccess { _totalCategorias.value = it.size }
                .onFailure { error ->
                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }

            expenseRepository.getExpenses(null, null)
                .onSuccess { _totalGastos.value = it.size }
                .onFailure { error ->
                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }

            budgetRepository.getBudgets(null, null)
                .onSuccess { _totalPresupuestos.value = it.size }
                .onFailure { error ->
                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }

            if (isRefresh) _refreshing.value = false
            else _loading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearToken()
        }
    }

}