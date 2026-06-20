package com.pdm0126.mibolsillo.data.api.fixedpayments

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FixedPaymentRequestDto(
    val nombre: String,
    @SerialName("categoria_id") val categoriaId: String,
    val monto: Double,
    @SerialName("dia_vencimiento") val diaVencimiento: Int,
    @SerialName("dias_recordatorio") val diasRecordatorio: Int
)