package com.pdm0126.mibolsillo.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseDto(
    val id: String,
    val nombre: String,
    val email: String
)