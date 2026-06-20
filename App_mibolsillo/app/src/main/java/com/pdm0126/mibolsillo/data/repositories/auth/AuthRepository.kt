package com.pdm0126.mibolsillo.data.repositories.auth

import com.pdm0126.mibolsillo.model.Session
import com.pdm0126.mibolsillo.model.User

interface AuthRepository {

    suspend fun register(
        nombre: String,
        email: String,
        password: String
    ): Result<User>

    suspend fun login(
        email: String,
        password: String
    ): Result<Session>
}