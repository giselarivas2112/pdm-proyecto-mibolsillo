package com.pdm0126.mibolsillo.data.repositories.expense

import com.pdm0126.mibolsillo.data.api.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.expense.ExpenseDto
import com.pdm0126.mibolsillo.data.api.expense.ExpenseRequestDto
import com.pdm0126.mibolsillo.data.api.expense.ExpenseResponseDto
import com.pdm0126.mibolsillo.data.api.expense.ExpenseUpdateResponseDto
import com.pdm0126.mibolsillo.data.api.expense.toModel
import com.pdm0126.mibolsillo.model.Expense
import com.pdm0126.mibolsillo.model.ExpenseCreationResult
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

class ExpenseApiRepository(
    private val sessionManager: SessionManager
) : ExpenseRepository {

    override suspend fun createExpense(
        categoriaId: String?, monto: Double, fecha: String, descripcion: String?
    ): Result<ExpenseCreationResult> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.post("gastos") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(ExpenseRequestDto(categoriaId, monto, fecha, descripcion))
            }

            if (response.status.isSuccess()) {
                val body = response.body<ExpenseResponseDto>()
                Result.success(ExpenseCreationResult(body.expense.toModel(), body.alerta))
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExpenses(month: Int?, year: Int?): Result<List<Expense>> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("gastos") {
                header("Authorization", "Bearer $token")
                parameter("month", month)
                parameter("year", year)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<List<ExpenseDto>>().map { it.toModel() })
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateExpense(
        id: String, categoriaId: String?, monto: Double, fecha: String, descripcion: String?
    ): Result<Expense> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.put("gastos/$id") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(ExpenseRequestDto(categoriaId, monto, fecha, descripcion))
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<ExpenseUpdateResponseDto>().expense.toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteExpense(id: String): Result<Unit> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.delete("gastos/$id") {
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}