package com.pdm0126.mibolsillo.data.api.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)