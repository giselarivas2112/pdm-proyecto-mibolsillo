package com.pdm0126.mibolsillo.data.api.auth.Login

import com.pdm0126.mibolsillo.data.api.auth.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val message: String,
    val token: String,
    val user: UserDto
)