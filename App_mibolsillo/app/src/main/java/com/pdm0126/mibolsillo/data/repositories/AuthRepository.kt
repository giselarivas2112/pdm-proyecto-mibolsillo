package com.pdm0126.mibolsillo.data.repositories

import com.pdm0126.mibolsillo.data.model.User

interface AuthRepository {

    suspend fun register(
        nombre: String,
        email: String,
        password: String
    ): Result<User>
}