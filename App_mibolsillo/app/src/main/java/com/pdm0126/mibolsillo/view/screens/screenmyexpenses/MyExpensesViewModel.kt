package com.pdm0126.mibolsillo.view.screens.screenmyexpenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseApiRepository
import com.pdm0126.mibolsillo.data.repositories.expense.ExpenseRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.data.model.Category
import com.pdm0126.mibolsillo.data.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyExpensesViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val expenseRepository: ExpenseRepository = ExpenseApiRepository(sessionManager)
    private val categoryRepository: CategoryRepository = CategoryApiRepository(sessionManager)

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses = _expenses.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

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

    private val _categoriaFiltro = MutableStateFlow("Todos")
    val categoriaFiltro = _categoriaFiltro.asStateFlow()

    val filteredExpenses = combine(_expenses, _categoriaFiltro) { expenses, filtro ->
        if (filtro == "Todos") expenses
        else expenses.filter { it.categoryName == filtro }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expensesGroupedByDate = filteredExpenses.map { expenses ->
        expenses.groupBy { it.fecha.take(10) }
            .toSortedMap(compareByDescending { it })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val totalGastado = filteredExpenses.map { it.sumOf { e -> e.monto } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val mayorGasto = filteredExpenses.map { it.maxOfOrNull { e -> e.monto } ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            expenseRepository.getExpenses(_mes.value, _anio.value)
                .onSuccess { _expenses.value = it }
                .onFailure { e -> _error.value = e.message }

            categoryRepository.getCategoriesByMonth(_mes.value, _anio.value)
                .onSuccess { _categories.value = it }
                .onFailure { _categories.value = emptyList() }

            _loading.value = false
        }
    }
    fun deleteExpense(id: String) {
        viewModelScope.launch {
            _deleting.value = true
            _error.value = null

            expenseRepository.deleteExpense(id)
                .onSuccess {
                    _expenses.value = _expenses.value.filter { it.id != id }
                }
                .onFailure { e -> _error.value = e.message }

            _deleting.value = false
        }
    }
    fun setCategoriaFiltro(categoria: String) {
        _categoriaFiltro.value = categoria
    }
    fun mesAnterior() {
        if (_mes.value == 1) { _mes.value = 12; _anio.value -= 1 }
        else _mes.value -= 1
        _categoriaFiltro.value = "Todos"
    }

    fun mesSiguiente() {
        if (_mes.value == 12) { _mes.value = 1; _anio.value += 1 }
        else _mes.value += 1
        _categoriaFiltro.value = "Todos"
    }
}