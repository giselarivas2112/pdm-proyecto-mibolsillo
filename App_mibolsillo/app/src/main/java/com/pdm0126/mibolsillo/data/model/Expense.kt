package com.pdm0126.mibolsillo.data.model

import com.pdm0126.mibolsillo.data.api.expense.AlertaDto

data class Expense(
    val id: String,
    val categoriaId: String?,
    val monto: Double,
    val fecha: String,
    val descripcion: String?,
    val categoryName: String?,
    val categoryIcon: String?
)

data class ExpenseCreationResult(
    val expense: Expense,
    val alerta: AlertaDto?
)