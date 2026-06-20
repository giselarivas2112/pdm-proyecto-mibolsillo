package com.pdm0126.mibolsillo.model

data class FixedPayment(
    val id: String,
    val nombre: String,
    val monto: Double,
    val diaVencimiento: Int,
    val diasRecordatorio: Int,
    val categoria: Category
)