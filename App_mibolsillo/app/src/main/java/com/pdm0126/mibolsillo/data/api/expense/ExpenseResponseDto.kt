package com.pdm0126.mibolsillo.data.api.expense

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseResponseDto(
    val message: String,
    val expense: ExpenseDto,
    val alerta: AlertaDto? = null
)

@Serializable
data class AlertaDto(
    val mensaje: String,
    @SerialName("porcentaje_usado") val porcentajeUsado: String,
    @SerialName("monto_limite") val montoLimite: Double,
    @SerialName("total_gastado") val totalGastado: Double
)

@Serializable
data class ExpenseUpdateResponseDto(
    val message: String,
    val expense: ExpenseDto
)