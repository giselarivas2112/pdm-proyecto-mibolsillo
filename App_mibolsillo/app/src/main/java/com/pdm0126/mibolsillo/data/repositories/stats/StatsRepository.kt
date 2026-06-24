package com.pdm0126.mibolsillo.data.repositories.stats

import com.pdm0126.mibolsillo.model.BudgetSummary
import com.pdm0126.mibolsillo.model.DailyExpenses
import com.pdm0126.mibolsillo.model.Distribution

interface StatsRepository {
    suspend fun getBudgetSummary(mes: Int, anio: Int): Result<BudgetSummary>
    suspend fun getDistribution(mes: Int, anio: Int): Result<Distribution>
    suspend fun getDailyExpenses(mes: Int, anio: Int): Result<DailyExpenses>
}
