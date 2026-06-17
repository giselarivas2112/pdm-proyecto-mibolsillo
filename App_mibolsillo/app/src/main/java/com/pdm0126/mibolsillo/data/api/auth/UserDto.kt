package com.pdm0126.mibolsillo.data.api.auth

import com.pdm0126.mibolsillo.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val nombre: String,
    val email: String
)
fun UserDto.toModel(): User = User(
    id = id,
    nombre = nombre,
    email = email
)
