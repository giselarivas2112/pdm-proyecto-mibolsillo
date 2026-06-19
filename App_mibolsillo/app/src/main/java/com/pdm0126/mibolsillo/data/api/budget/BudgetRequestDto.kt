package com.pdm0126.mibolsillo.data.api.budget

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetRequestDto(
    @SerialName("categoria_id") val categoriaId: String,
    @SerialName("monto_limite") val montoLimite: Double,
    val mes: Int,
    val anio: Int,
    @SerialName("alerta_porcentaje") val alertaPorcentaje: Int,
    val notas: String? = null
)