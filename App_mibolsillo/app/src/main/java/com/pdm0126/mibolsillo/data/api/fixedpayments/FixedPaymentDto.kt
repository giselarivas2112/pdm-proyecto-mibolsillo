package com.pdm0126.mibolsillo.data.api.fixedpayments

import com.pdm0126.mibolsillo.data.model.Category
import com.pdm0126.mibolsillo.data.model.FixedPayment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FixedPaymentDto(
    val id: String,
    val nombre: String,
    val monto: Double,
    @SerialName("dia_vencimiento") val diaVencimiento: Int,
    @SerialName("dias_recordatorio") val diasRecordatorio: Int,
    val categorias: FixedPaymentCategoryDto
)

@Serializable
data class FixedPaymentCategoryDto(
    val id: String,
    val nombre: String,
    val icono: String
)

fun FixedPaymentDto.toModel(): FixedPayment = FixedPayment(
    id = id,
    nombre = nombre,
    monto = monto,
    diaVencimiento = diaVencimiento,
    diasRecordatorio = diasRecordatorio,
    categoria = Category(
        id = categorias.id,
        nombre = categorias.nombre,
        icono = categorias.icono
    )
)