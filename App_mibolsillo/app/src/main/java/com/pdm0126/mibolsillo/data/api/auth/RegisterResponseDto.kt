package com.pdm0126.mibolsillo.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val message: String,
    val user: UserDto
)