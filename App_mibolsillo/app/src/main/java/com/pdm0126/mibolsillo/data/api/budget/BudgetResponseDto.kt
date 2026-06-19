package com.pdm0126.mibolsillo.data.api.budget

import kotlinx.serialization.Serializable

@Serializable
data class BudgetResponseDto(
    val message: String,
    val budget: BudgetDto
)