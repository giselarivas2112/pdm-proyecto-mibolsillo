package com.pdm0126.mibolsillo.data.api.Stats

import com.pdm0126.mibolsillo.data.model.BudgetCategoryResume
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetCategoryResumeDto(
    @SerialName("presupuesto_id") val presupuestoId: String,
    @SerialName("categoria") val categoria: CategoriaResumeDto,
    @SerialName("limite") val limite: Double,
    @SerialName("gastado") val gastado: Double,
    @SerialName("disponible") val disponible: Double,
    @SerialName("porcentaje_usado") val porcentajeUsado: Double,
    @SerialName("alerta_porcentaje") val alertaPorcentaje: Int,
    @SerialName("estado") val estado: String,
    @SerialName("notas") val notas: String?
)

@Serializable
data class CategoriaResumeDto(
    @SerialName("id") val id: String,
    @SerialName("nombre") val nombre: String,
    @SerialName("icono") val icono: String
)

fun BudgetCategoryResumeDto.toModel() = BudgetCategoryResume(
    presupuestoId = presupuestoId,
    categoriaNombre = categoria.nombre,
    categoriaIcono = categoria.icono,
    limite = limite,
    gastado = gastado,
    disponible = disponible,
    porcentajeUsado = porcentajeUsado,
    alertaPorcentaje = alertaPorcentaje,
    estado = estado,
    notas = notas
)