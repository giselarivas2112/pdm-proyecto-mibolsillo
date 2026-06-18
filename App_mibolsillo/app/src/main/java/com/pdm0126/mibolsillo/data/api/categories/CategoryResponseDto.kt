package com.pdm0126.mibolsillo.data.api.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    val message: String,
    val category: CategoryDto
)