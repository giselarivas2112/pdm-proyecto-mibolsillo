package com.pdm0126.mibolsillo.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val nombre: String,
    val email: String,
    val password: String
)