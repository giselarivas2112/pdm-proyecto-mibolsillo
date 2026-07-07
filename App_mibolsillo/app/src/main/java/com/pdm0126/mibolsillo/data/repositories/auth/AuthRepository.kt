package com.pdm0126.mibolsillo.data.repositories.auth

import com.pdm0126.mibolsillo.data.api.auth.ProfileResponseDto
import com.pdm0126.mibolsillo.data.model.Session
import com.pdm0126.mibolsillo.data.model.User

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

    suspend fun saveOneSignalId(
        token: String,
        oneSignalId: String
    ): Result<Unit>

    suspend fun getProfile(
        token: String
    ): Result<ProfileResponseDto>
}
