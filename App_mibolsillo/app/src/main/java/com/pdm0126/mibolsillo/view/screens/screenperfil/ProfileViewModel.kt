package com.pdm0126.mibolsillo.view.screens.screenperfil

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

    fun loadProfileData() {
        viewModelScope.launch {
            _loading.value = true

            val token = sessionManager.getToken() ?: return@launch

            authRepository.getProfile(token)
                .onSuccess { _nombreUsuario.value = it.nombre }

            categoryRepository.getCategories()
                .onSuccess { _totalCategorias.value = it.size }

            expenseRepository.getExpenses(null, null)
                .onSuccess { _totalGastos.value = it.size }

            budgetRepository.getBudgets(null, null)
                .onSuccess { _totalPresupuestos.value = it.size }

            _loading.value = false
        }
    }

    fun logout() {

        viewModelScope.launch {

            sessionManager.clearToken()

        }
    }
}