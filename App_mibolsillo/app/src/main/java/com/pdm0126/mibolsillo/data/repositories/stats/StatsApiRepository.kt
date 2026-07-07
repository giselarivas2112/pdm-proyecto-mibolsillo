package com.pdm0126.mibolsillo.data.repositories.stats

import com.pdm0126.mibolsillo.data.api.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.Stats.BudgetSummaryResponseDto
import com.pdm0126.mibolsillo.data.api.Stats.DailyExpensesResponseDto
import com.pdm0126.mibolsillo.data.api.Stats.DistributionResponseDto
import com.pdm0126.mibolsillo.data.api.Stats.toModel
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.pdm0126.mibolsillo.data.model.BudgetSummary
import com.pdm0126.mibolsillo.data.model.DailyExpenses
import com.pdm0126.mibolsillo.data.model.Distribution
import com.tupaquete.mibolsillo.data.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess

class StatsApiRepository(
    private val sessionManager: SessionManager
) : StatsRepository {

    override suspend fun  getBudgetSummary(mes: Int, anio: Int): Result<BudgetSummary> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("estadisticas/resumen") {
                header("Authorization", "Bearer $token")
                parameter("mes", mes)
                parameter("anio", anio)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<BudgetSummaryResponseDto>().toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDistribution(mes: Int, anio: Int): Result<Distribution> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("estadisticas/distribucion") {
                header("Authorization", "Bearer $token")
                parameter("mes", mes)
                parameter("anio", anio)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<DistributionResponseDto>().toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDailyExpenses(mes: Int, anio: Int): Result<DailyExpenses> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("estadisticas/gastos-diarios") {
                header("Authorization", "Bearer $token")
                parameter("mes", mes)
                parameter("anio", anio)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<DailyExpensesResponseDto>().toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}