package com.pdm0126.mibolsillo.data.repositories

import com.pdm0126.mibolsillo.data.api.auth.Login.LoginRequestDto
import com.pdm0126.mibolsillo.data.api.auth.Login.LoginResponseDto
import com.pdm0126.mibolsillo.data.api.auth.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.auth.Registrarse.RegisterRequestDto
import com.pdm0126.mibolsillo.data.api.auth.Registrarse.RegisterResponseDto
import com.pdm0126.mibolsillo.data.api.auth.toModel
import com.pdm0126.mibolsillo.data.api.auth.toSession
import com.pdm0126.mibolsillo.data.model.Session
import com.pdm0126.mibolsillo.data.model.User
import com.tupaquete.mibolsillo.data.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AuthApiRepository : AuthRepository {

    override suspend fun register(
        nombre: String,
        email: String,
        password: String
    ): Result<User> {

        return try {

            val response = KtorClient.client.post(
                "auth/register"
            ) {

                contentType(ContentType.Application.Json)

                setBody(
                    RegisterRequestDto(
                        nombre = nombre,
                        email = email,
                        password = password
                    )
                )
            }

            if (response.status.isSuccess()) {

                val registerResponse =
                    response.body<RegisterResponseDto>()

                Result.success(
                    registerResponse.user.toModel()
                )

            } else {

                val errorResponse =
                    response.body<ErrorResponseDto>()

                Result.failure(
                    Exception(errorResponse.error)
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }


    override suspend fun login(
        email: String,
        password: String
    ): Result<Session> {

        return try {

            val response = KtorClient.client.post(
                "auth/login"
            ) {

                contentType(ContentType.Application.Json)

                setBody(
                    LoginRequestDto(
                        email = email,
                        password = password
                    )
                )
            }

            if (response.status.isSuccess()) {

                val loginResponse =
                    response.body<LoginResponseDto>()

                Result.success(
                    loginResponse.toSession()
                )

            } else {

                val errorResponse =
                    response.body<ErrorResponseDto>()

                Result.failure(
                    Exception(errorResponse.error)
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}