package com.pdm0126.mibolsillo.data.api.budget

import com.pdm0126.mibolsillo.model.Budget
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetDto(
    val id: String,
    @SerialName("categoria_id") val categoriaId: String,
    @SerialName("monto_limite") val montoLimite: Double,
    val mes: Int,
    val anio: Int,
    @SerialName("alerta_porcentaje") val alertaPorcentaje: Int,
    val notas: String? = null,
    val categorias: BudgetCategoryDto? = null
)

@Serializable
data class BudgetCategoryDto(
    val id: String,
    val nombre: String,
    val icono: String
)

fun BudgetDto.toModel(): Budget {
    return Budget(
        id = id,
        categoriaId = categoriaId,
        montoLimite = montoLimite,
        mes = mes,
        anio = anio,
        alertaPorcentaje = alertaPorcentaje,
        notas = notas,
        categoryName = categorias?.nombre,
        categoryIcon = categorias?.icono
    )
}