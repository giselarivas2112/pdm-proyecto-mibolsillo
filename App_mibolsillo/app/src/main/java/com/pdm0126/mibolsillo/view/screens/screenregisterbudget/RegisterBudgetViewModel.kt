package com.pdm0126.mibolsillo.view.screens.screenregisterbudget


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.mibolsillo.data.model.Category
import com.pdm0126.mibolsillo.data.repositories.budget.BudgetApiRepository
import com.pdm0126.mibolsillo.data.repositories.budget.BudgetRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryApiRepository
import com.pdm0126.mibolsillo.data.repositories.categories.CategoryRepository
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterBudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)
    private val repository: BudgetRepository = BudgetApiRepository(sessionManager)
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

            categoryRepository.getCategories()
                .onSuccess { _categories.value = it }
                .onFailure { error ->
                    _errorCategories.value =
                        error.message ?: "No se pudieron cargar las categorías"
                }

            if (isRefresh) _refreshing.value = false
            else _loadingCategories.value = false
        }
    }

    fun createBudget(
        categoriaId: String, montoLimite: Double, mes: Int, anio: Int,
        alertaPorcentaje: Int, notas: String?
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            repository.createBudget(categoriaId, montoLimite, mes, anio, alertaPorcentaje, notas)
                .onSuccess { _success.value = true }
                .onFailure { error ->
                    _error.value =
                        error.message ?: "Ocurrió un error. Intenta nuevamente"
                }

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