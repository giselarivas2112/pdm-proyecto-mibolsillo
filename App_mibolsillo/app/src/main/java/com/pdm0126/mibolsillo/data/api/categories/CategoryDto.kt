package com.pdm0126.mibolsillo.data.api.categories

import com.pdm0126.mibolsillo.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    @SerialName("usuario_id") val usuarioId: String? = null,
    val nombre: String,
    val icono: String
)
fun CategoryDto.toModel(): Category {
    return Category(
        id = id,
        nombre = nombre,
        icono = icono
    )
}