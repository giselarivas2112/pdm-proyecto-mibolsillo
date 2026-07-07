package com.pdm0126.mibolsillo.data.api.expense

import com.pdm0126.mibolsillo.data.model.Expense
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseDto(
    val id: String,
    @SerialName("categoria_id") val categoriaId: String? = null,
    val monto: Double,
    val fecha: String,
    val descripcion: String? = null,
    val categorias: ExpenseCategoryDto? = null
)

@Serializable
data class ExpenseCategoryDto(
    val id: String,
    val nombre: String,
    val icono: String
)

fun ExpenseDto.toModel(): Expense {
    return Expense(
        id = id,
        categoriaId = categoriaId,
        monto = monto,
        fecha = fecha,
        descripcion = descripcion,
        categoryName = categorias?.nombre,
        categoryIcon = categorias?.icono
    )
}