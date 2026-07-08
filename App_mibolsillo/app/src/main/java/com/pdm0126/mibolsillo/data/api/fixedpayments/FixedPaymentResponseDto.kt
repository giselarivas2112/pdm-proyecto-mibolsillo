package com.pdm0126.mibolsillo.data.api.fixedpayments

import kotlinx.serialization.Serializable

@Serializable
data class FixedPaymentResponseDto(
    val message: String,
    val pago: FixedPaymentDto
)