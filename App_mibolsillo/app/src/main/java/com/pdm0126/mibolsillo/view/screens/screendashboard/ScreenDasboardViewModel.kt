package com.pdm0126.mibolsillo.view.screens.screendashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.auth.AuthApiRepository
import com.pdm0126.mibolsillo.data.repositories.auth.AuthRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseApiRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseRepository
import com.pdm0126.mibolsillo.data.repositories.stats.StatsApiRepository
import com.pdm0126.mibolsillo.data.repositories.stats.StatsRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.model.BudgetSummary
import com.pdm0126.mibolsillo.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val statsRepository: StatsRepository = StatsApiRepository(sessionManager)
    private val expenseRepository: ExpenseRepository = ExpenseApiRepository(sessionManager)
    private val authRepository: AuthRepository = AuthApiRepository()

    private val _nombreUsuario = MutableStateFlow("")
    val nombreUsuario = _nombreUsuario.asStateFlow()

    private val _summary = MutableStateFlow<BudgetSummary?>(null)
    val summary = _summary.asStateFlow()

    private val _recentExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val recentExpenses = _recentExpenses.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val now = java.util.Calendar.getInstance()
    private val mesActual = now.get(java.util.Calendar.MONTH) + 1
    private val anioActual = now.get(java.util.Calendar.YEAR)
    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) _refreshing.value = true
            else _loading.value = true
            _error.value = null

            val token = sessionManager.getToken()

            if (token != null) {
                authRepository.getProfile(token)
                    .onSuccess { _nombreUsuario.value = it.nombre }
                    .onFailure { e -> _error.value = e.message }
            }

            statsRepository.getBudgetSummary(mesActual, anioActual)
                .onSuccess { _summary.value = it }
                .onFailure { e -> _error.value = e.message }

            expenseRepository.getExpenses(mesActual, anioActual)
                .onSuccess { lista ->
                    _recentExpenses.value = lista
                        .sortedByDescending { it.fecha }
                        .take(5)
                }
                .onFailure { e -> _error.value = e.message }

            if (isRefresh) _refreshing.value = false
            else _loading.value = false
        }
    }
}