package com.pdm0126.mibolsillo.data.repositories.categories

import com.pdm0126.mibolsillo.data.api.ErrorResponseDto
import com.pdm0126.mibolsillo.data.api.categories.CategoryDto
import com.pdm0126.mibolsillo.data.api.categories.CategoryRequestDto
import com.pdm0126.mibolsillo.data.api.categories.CategoryResponseDto
import com.pdm0126.mibolsillo.data.api.categories.toModel
import com.pdm0126.mibolsillo.model.Category
import com.pdm0126.mibolsillo.data.session.SessionManager
import com.tupaquete.mibolsillo.data.api.KtorClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class CategoryApiRepository(
    private val sessionManager: SessionManager
) : CategoryRepository {

    override suspend fun createCategory(nombre: String, icono: String): Result<Category> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.post("categorias") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(CategoryRequestDto(nombre = nombre, icono = icono))
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<CategoryResponseDto>().category.toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("categorias") {
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<List<CategoryDto>>().map { it.toModel() })
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCategory(id: String, nombre: String, icono: String): Result<Category> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.put("categorias/$id") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(CategoryRequestDto(nombre = nombre, icono = icono))
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<CategoryResponseDto>().category.toModel())
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCategory(id: String): Result<Unit> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.delete("categorias/$id") {
                header("Authorization", "Bearer $token")
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getCategoriesByMonth(mes: Int, anio: Int): Result<List<Category>> {
        return try {
            val token = sessionManager.getToken()

            val response = KtorClient.client.get("categorias/month") {
                header("Authorization", "Bearer $token")
                parameter("mes", mes)
                parameter("anio", anio)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body<List<CategoryDto>>().map { it.toModel() })
            } else {
                Result.failure(Exception(response.body<ErrorResponseDto>().error))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}