package com.pdm0126.mibolsillo.data.repositories.categories

import com.pdm0126.mibolsillo.data.model.Category

interface CategoryRepository {

    suspend fun createCategory(nombre: String, icono: String): Result<Category>

    suspend fun getCategories(): Result<List<Category>>

    suspend fun updateCategory(id: String, nombre: String, icono: String): Result<Category>

    suspend fun deleteCategory(id: String): Result<Unit>
}