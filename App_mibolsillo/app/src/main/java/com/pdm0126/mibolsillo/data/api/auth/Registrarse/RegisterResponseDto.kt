package com.pdm0126.mibolsillo.data.api.auth.Registrarse

import com.pdm0126.mibolsillo.data.api.auth.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val message: String,
    val user: UserDto
)