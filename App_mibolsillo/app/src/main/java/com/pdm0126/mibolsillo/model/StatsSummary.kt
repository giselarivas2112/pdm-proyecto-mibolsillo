package com.pdm0126.mibolsillo.model

data class BudgetSummary(
    val mes: Int,
    val anio: Int,
    val totalPresupuestado: Double,
    val totalGastado: Double,
    val totalDisponible: Double,
    val porcentajeGlobal: Double,
    val categorias: List<BudgetCategoryResume>
)
data class BudgetCategoryResume(
    val presupuestoId: String,
    val categoriaNombre: String,
    val categoriaIcono: String,
    val limite: Double,
    val gastado: Double,
    val disponible: Double,
    val porcentajeUsado: Double,
    val alertaPorcentaje: Int,
    val estado: String
)