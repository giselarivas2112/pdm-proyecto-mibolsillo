package com.pdm0126.mibolsillo.view.screens.screenstats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.stats.StatsApiRepository
import com.pdm0126.mibolsillo.data.repositories.stats.StatsRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.model.BudgetSummary
import com.pdm0126.mibolsillo.model.DailyExpenses
import com.pdm0126.mibolsillo.model.Distribution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReportsViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: StatsRepository = StatsApiRepository(sessionManager)

    private val _summary = MutableStateFlow<BudgetSummary?>(null)
    val summary = _summary.asStateFlow()

    private val _distribution = MutableStateFlow<Distribution?>(null)
    val distribution = _distribution.asStateFlow()

    private val _dailyExpenses = MutableStateFlow<DailyExpenses?>(null)
    val dailyExpenses = _dailyExpenses.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val now = java.util.Calendar.getInstance()
    private val _mes = MutableStateFlow(now.get(java.util.Calendar.MONTH) + 1)
    val mes = _mes.asStateFlow()

    private val _anio = MutableStateFlow(now.get(java.util.Calendar.YEAR))
    val anio = _anio.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.getBudgetSummary(_mes.value, _anio.value)
                .onSuccess { _summary.value = it }
                .onFailure { e -> _error.value = e.message }

            repository.getDistribution(_mes.value, _anio.value)
                .onSuccess { _distribution.value = it }
                .onFailure { e -> _error.value = e.message }

            repository.getDailyExpenses(_mes.value, _anio.value)
                .onSuccess { _dailyExpenses.value = it }
                .onFailure { e -> _error.value = e.message }

            _loading.value = false
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