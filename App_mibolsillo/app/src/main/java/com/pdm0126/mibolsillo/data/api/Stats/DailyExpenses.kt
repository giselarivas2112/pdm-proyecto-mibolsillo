package com.pdm0126.mibolsillo.data.api.Stats

import com.pdm0126.mibolsillo.model.DailyExpenses
import com.pdm0126.mibolsillo.model.DailyExpensesR
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyExpensesResponseDto(
    @SerialName("mes") val mes: Int,
    @SerialName("anio") val anio: Int,
    @SerialName("dias") val dias: List<DailyExpensesDto>
)

@Serializable
data class DailyExpensesDto(
    @SerialName("dia") val dia: Int,
    @SerialName("total") val total: Double
)

fun DailyExpensesResponseDto.toModel() = DailyExpenses(
    mes = mes,
    anio = anio,
    dias = dias.map { it.toModel() }
)

fun DailyExpensesDto.toModel() = DailyExpensesR(
    dia = dia,
    total = total
)
