package com.pdm0126.mibolsillo.data.api.Stats

import com.pdm0126.mibolsillo.data.model.BudgetSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.collections.map


@Serializable
data class BudgetSummaryResponseDto(
    @SerialName("mes") val mes: Int,
    @SerialName("anio") val anio: Int,
    @SerialName("totales") val totales: TotalesDto,
    @SerialName("categorias") val categorias: List<BudgetCategoryResumeDto>
)

@Serializable
data class TotalesDto(
    @SerialName("total_presupuestado") val totalPresupuestado: Double,
    @SerialName("total_gastado") val totalGastado: Double,
    @SerialName("total_disponible") val totalDisponible: Double,
    @SerialName("porcentaje_global") val porcentajeGlobal: Double?
)

fun BudgetSummaryResponseDto.toModel() = BudgetSummary(
    mes = mes,
    anio = anio,
    totalPresupuestado = totales.totalPresupuestado,
    totalGastado = totales.totalGastado,
    totalDisponible = totales.totalDisponible,
    porcentajeGlobal = totales.porcentajeGlobal ?: 0.0,
    categorias = categorias.map { it.toModel() }
)