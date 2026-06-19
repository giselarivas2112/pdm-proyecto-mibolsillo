package com.pdm0126.mibolsillo.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val error: String
)