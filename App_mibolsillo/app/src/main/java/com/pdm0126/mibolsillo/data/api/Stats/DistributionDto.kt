package com.pdm0126.mibolsillo.data.api.Stats

import com.pdm0126.mibolsillo.data.model.Distribution
import com.pdm0126.mibolsillo.data.model.DistributionItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.collections.map

@Serializable
data class DistributionResponseDto(
    @SerialName("mes") val mes: Int,
    @SerialName("anio") val anio: Int,
    @SerialName("total_gastado") val totalGastado: Double,
    @SerialName("distribucion") val distribucion: List<DistributionItemDto>
)

@Serializable
data class DistributionItemDto(
    @SerialName("nombre") val nombre: String,
    @SerialName("icono") val icono: String?,
    @SerialName("total") val total: Double,
    @SerialName("porcentaje") val porcentaje: Double
)

fun DistributionResponseDto.toModel() = Distribution(
    mes = mes,
    anio = anio,
    totalGastado = totalGastado,
    distribucion = distribucion.map { it.toModel() }
)

fun DistributionItemDto.toModel() = DistributionItem(
    nombre = nombre,
    icono = icono,
    total = total,
    porcentaje = porcentaje
)