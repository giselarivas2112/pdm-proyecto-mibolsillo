package com.pdm0126.mibolsillo.model

data class Distribution(
    val mes: Int,
    val anio: Int,
    val totalGastado: Double,
    val distribucion: List<DistributionItem>
)

data class DistributionItem(
    val nombre: String,
    val icono: String?,
    val total: Double,
    val porcentaje: Double
)