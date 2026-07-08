package com.pdm0126.mibolsillo.data.repositories.fixedpayments

import com.pdm0126.mibolsillo.data.model.FixedPayment

interface FixedPaymentRepository {

    suspend fun createFixedPayment(
        nombre: String,
        categoriaId: String,
        monto: Double,
        diaVencimiento: Int,
        diasRecordatorio: Int
    ): Result<FixedPayment>

    suspend fun getFixedPayments(): Result<List<FixedPayment>>

    suspend fun deleteFixedPayment(id: String): Result<Unit>
}