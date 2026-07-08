package com.pdm0126.mibolsillo.view.screens.screenstats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.stats.StatsApiRepository
import com.pdm0126.mibolsillo.data.repositories.stats.StatsRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.data.model.BudgetSummary
import com.pdm0126.mibolsillo.data.model.DailyExpenses
import com.pdm0126.mibolsillo.data.model.Distribution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsViewModel(application: Application) : AndroidViewModel(application) {

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

    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    private val now = java.util.Calendar.getInstance()
    private val _mes = MutableStateFlow(now.get(java.util.Calendar.MONTH) + 1)
    val mes = _mes.asStateFlow()

    private val _anio = MutableStateFlow(now.get(java.util.Calendar.YEAR))
    val anio = _anio.asStateFlow()

    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _refreshing.value = true
            } else {
                _loading.value = true

                _summary.value = null
                _distribution.value = null
                _dailyExpenses.value = null
            }

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

            if (isRefresh) _refreshing.value = false
            else _loading.value = false
        }
    }

    fun mesAnterior() {
        if (_mes.value == 1) { _mes.value = 12; _anio.value -= 1 }
        else _mes.value -= 1
    }

    fun mesSiguiente() {
        if (_mes.value == 12) { _mes.value = 1; _anio.value += 1 }
        else _mes.value += 1
    }
}