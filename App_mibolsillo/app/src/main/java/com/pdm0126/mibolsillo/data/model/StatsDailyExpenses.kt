package com.pdm0126.mibolsillo.data.model

data class DailyExpenses(
    val mes: Int,
    val anio: Int,
    val dias: List<DailyExpensesR>
)

data class DailyExpensesR(
    val dia: Int,
    val total: Double
)