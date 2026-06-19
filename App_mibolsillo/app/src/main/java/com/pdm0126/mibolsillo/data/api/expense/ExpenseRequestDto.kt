package com.pdm0126.mibolsillo.data.api.expense

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseRequestDto(
    @SerialName("categoria_id") val categoriaId: String? = null,
    val monto: Double,
    val fecha: String,
    val descripcion: String? = null
)