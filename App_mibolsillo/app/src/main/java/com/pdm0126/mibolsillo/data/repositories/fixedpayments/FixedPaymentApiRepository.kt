package com.pdm0126.mibolsillo.data.repositories.fixedpayments

import com.pdm0126.mibolsillo.data.api.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.fixedpayments.FixedPaymentDto
import com.pdm0126.mibolsillo.data.api.fixedpayments.FixedPaymentRequestDto
import com.pdm0126.mibolsillo.data.api.fixedpayments.FixedPaymentResponseDto
import com.pdm0126.mibolsillo.data.api.fixedpayments.toModel
import com.pdm0126.mibolsillo.data.model.FixedPayment
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.tupaquete.mibolsillo.data.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class FixedPaymentApiRepository(
    private val sessionManager: SessionManager
) : FixedPaymentRepository {

    override suspend fun createFixedPayment(
        nombre: String,
        categoriaId: String,
        monto: Double,
        diaVencimiento: Int,
        diasRecordatorio: Int
    ): Result<FixedPayment> {
        return try {
            val token = sessionManager.getToken()
            val response = KtorClient.client.post("pagos-fijos") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(FixedPaymentRequestDto(
                    nombre = nombre,
                    categoriaId = categoriaId,
                    monto = monto,
                    diaVencimiento = diaVencimiento,
                    diasRecordatorio = diasRecordatorio
                ))
            }
            if (response.status.isSuccess()) {
                Result.success(response.body<FixedPaymentResponseDto>().pago.toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Ocurrió un error. Intenta recargar")
            )
        }
    }

    override suspend fun getFixedPayments(): Result<List<FixedPayment>> {
        return try {
            val token = sessionManager.getToken()
            val response = KtorClient.client.get("pagos-fijos") {
                header("Authorization", "Bearer $token")
            }
            if (response.status.isSuccess()) {
                Result.success(response.body<List<FixedPaymentDto>>().map { it.toModel() })
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(
                Exception("Ocurrió un error. Intenta recargar")
            )
        }
    }

    override suspend fun deleteFixedPayment(id: String): Result<Unit> {
        return try {
            val token = sessionManager.getToken()
            val response = KtorClient.client.delete("pagos-fijos/$id") {
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