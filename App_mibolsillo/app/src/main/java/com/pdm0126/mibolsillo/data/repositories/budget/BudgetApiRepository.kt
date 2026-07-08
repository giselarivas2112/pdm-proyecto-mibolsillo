package com.pdm0126.mibolsillo.data.repositories.budget

import com.pdm0126.mibolsillo.data.api.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.budget.BudgetDto
import com.pdm0126.mibolsillo.data.api.budget.BudgetRequestDto
import com.pdm0126.mibolsillo.data.api.budget.BudgetResponseDto
import com.pdm0126.mibolsillo.data.api.budget.toModel
import com.pdm0126.mibolsillo.data.model.Budget
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.tupaquete.mibolsillo.data.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class BudgetApiRepository(
    private val sessionManager: SessionManager
) : BudgetRepository {

    override suspend fun createBudget(
        categoriaId: String, montoLimite: Double, mes: Int, anio: Int,
        alertaPorcentaje: Int, notas: String?
    ): Result<Budget> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.post("presupuestos") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(BudgetRequestDto(categoriaId, montoLimite, mes, anio, alertaPorcentaje, notas))
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<BudgetResponseDto>().budget.toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Ocurrió un error. Intenta recargar")
            )
        }
    }

    override suspend fun getBudgets(mes: Int?, anio: Int?): Result<List<Budget>> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("presupuestos") {
                header("Authorization", "Bearer $token")
                parameter("mes", mes)
                parameter("anio", anio)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<List<BudgetDto>>().map { it.toModel() })
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Ocurrió un error. Intenta recargar")
            )
        }
    }

    override suspend fun updateBudget(
        id: String, categoriaId: String, montoLimite: Double, mes: Int, anio: Int,
        alertaPorcentaje: Int, notas: String?
    ): Result<Budget> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.put("presupuestos/$id") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(BudgetRequestDto(categoriaId, montoLimite, mes, anio, alertaPorcentaje, notas))
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<BudgetResponseDto>().budget.toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Ocurrió un error. Intenta recargar")
            )
        }
    }

    override suspend fun deleteBudget(id: String): Result<Unit> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.delete("presupuestos/$id") {
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Ocurrió un error. Intenta recargar")
            )
        }
    }
}