package com.pdm0126.mibolsillo.view.screens.screenviewbudgets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.stats.StatsApiRepository
import com.pdm0126.mibolsillo.data.repositories.stats.StatsRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.data.model.BudgetSummary
import com.pdm0126.mibolsillo.data.repositories.budget.BudgetApiRepository
import com.pdm0126.mibolsillo.data.repositories.budget.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: StatsRepository = StatsApiRepository(sessionManager)
    private val budgetRepository: BudgetRepository = BudgetApiRepository(sessionManager)

    private val _summary = MutableStateFlow<BudgetSummary?>(null)
    val summary = _summary.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val _deleting = MutableStateFlow(false)
    val deleting = _deleting.asStateFlow()

    private val now = java.util.Calendar.getInstance()
    private val _mes = MutableStateFlow(now.get(java.util.Calendar.MONTH) + 1)
    val mes = _mes.asStateFlow()

    private val _anio = MutableStateFlow(now.get(java.util.Calendar.YEAR))
    val anio = _anio.asStateFlow()

    init {
        loadData()
    }
    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) _refreshing.value = true
            else _loading.value = true
            _error.value = null

            repository.getBudgetSummary(_mes.value, _anio.value)
                .onSuccess { _summary.value = it }
                .onFailure { e -> _error.value = e.message }

            if (isRefresh) _refreshing.value = false
            else _loading.value = false
        }
    }
    fun deleteBudget(id: String) {
        viewModelScope.launch {
            _deleting.value = true
            _error.value = null

            budgetRepository.deleteBudget(id)
                .onSuccess {
                    repository.getBudgetSummary(_mes.value, _anio.value)
                        .onSuccess { _summary.value = it }
                        .onFailure { e -> _error.value = e.message }
                }
                .onFailure { e -> _error.value = e.message }

            _deleting.value = false
        }
    }
    fun mesAnterior() {
        if (_mes.value == 1) { _mes.value = 12; _anio.value -= 1 }
        else _mes.value -= 1
        loadData()
    }
    fun mesSiguiente() {
        if (_mes.value == 12) { _mes.value = 1; _anio.value += 1 }
        else _mes.value += 1
        loadData()
    }
}
