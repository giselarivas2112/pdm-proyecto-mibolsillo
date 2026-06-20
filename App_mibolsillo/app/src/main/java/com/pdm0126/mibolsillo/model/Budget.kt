package com.pdm0126.mibolsillo.model

data class Budget(
    val id: String,
    val categoriaId: String,
    val montoLimite: Double,
    val mes: Int,
    val anio: Int,
    val alertaPorcentaje: Int,
    val notas: String?,
    val categoryName: String?,
    val categoryIcon: String?
)