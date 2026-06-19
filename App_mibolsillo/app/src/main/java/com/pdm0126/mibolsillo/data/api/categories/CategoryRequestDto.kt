package com.pdm0126.mibolsillo.data.api.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequestDto(
    val nombre: String,
    val icono: String
)