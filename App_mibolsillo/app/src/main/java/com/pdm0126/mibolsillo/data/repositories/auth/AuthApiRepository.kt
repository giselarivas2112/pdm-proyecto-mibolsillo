package com.pdm0126.mibolsillo.data.repositories.auth

import com.pdm0126.mibolsillo.data.api.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.auth.OneSignalRequestDto
import com.pdm0126.mibolsillo.data.api.auth.ProfileResponseDto
import com.pdm0126.mibolsillo.data.api.auth.login.LoginRequestDto
import com.pdm0126.mibolsillo.data.api.auth.login.LoginResponseDto
import com.pdm0126.mibolsillo.data.api.auth.register.RegisterRequestDto
import com.pdm0126.mibolsillo.data.api.auth.register.RegisterResponseDto
import com.pdm0126.mibolsillo.data.api.auth.toModel
import com.pdm0126.mibolsillo.data.api.auth.toSession
import com.pdm0126.mibolsillo.model.Session
import com.pdm0126.mibolsillo.model.User
import com.tupaquete.mibolsillo.data.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.headers
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

    override suspend fun saveOneSignalId(
        token: String,
        oneSignalId: String
    ): Result<Unit> {

        return try {

            val response = KtorClient.client.put(
                "auth/onesignal"
            ) {

                contentType(ContentType.Application.Json)

                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer $token"
                    )
                }

                setBody(
                    OneSignalRequestDto(
                        onesignal_id = oneSignalId
                    )
                )
            }

            if (response.status.isSuccess()) {

                Result.success(Unit)

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

    override suspend fun getProfile(
        token: String
    ): Result<ProfileResponseDto> {

        return try {

            val response = KtorClient.client.get(
                "auth/profile"
            ) {

                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer $token"
                    )
                }
            }

            if (response.status.isSuccess()) {

                Result.success(
                    response.body<ProfileResponseDto>()
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
