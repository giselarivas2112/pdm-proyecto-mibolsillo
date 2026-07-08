package com.pdm0126.mibolsillo.data.repositories.expense

import com.pdm0126.mibolsillo.data.model.Expense
import com.pdm0126.mibolsillo.data.model.ExpenseCreationResult

interface ExpenseRepository {

    suspend fun createExpense(
        categoriaId: String?,
        monto: Double,
        fecha: String,
        descripcion: String?
    ): Result<ExpenseCreationResult>

    suspend fun getExpenses(month: Int? = null, year: Int? = null): Result<List<Expense>>

    suspend fun deleteExpense(id: String): Result<Unit>
}