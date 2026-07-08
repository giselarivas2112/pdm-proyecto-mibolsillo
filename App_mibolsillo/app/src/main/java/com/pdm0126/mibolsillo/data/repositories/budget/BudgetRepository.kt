package com.pdm0126.mibolsillo.data.repositories.budget

import com.pdm0126.mibolsillo.data.model.Budget

interface BudgetRepository {

    suspend fun createBudget(
        categoriaId: String,
        montoLimite: Double,
        mes: Int,
        anio: Int,
        alertaPorcentaje: Int,
        notas: String?
    ): Result<Budget>

    suspend fun getBudgets(mes: Int? = null, anio: Int? = null): Result<List<Budget>>

    suspend fun deleteBudget(id: String): Result<Unit>
}