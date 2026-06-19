package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpensesTodaySection() {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "HOY",
            color = Color(0xFF8A2BE2),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ExpenseRow(
            nombre = "McDonald's",
            categoria = "Comida",
            monto = "145",
            icon = Icons.Default.Fastfood
        )
        ExpenseRow(
            nombre = "CFE - Luz",
            categoria = "Servicios",
            monto = "380",
            icon = Icons.Default.Lightbulb
        )
    }
}