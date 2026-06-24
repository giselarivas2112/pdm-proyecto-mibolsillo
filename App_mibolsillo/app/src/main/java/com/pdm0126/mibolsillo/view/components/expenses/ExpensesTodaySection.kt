package com.pdm0126.mibolsillo.view.components.expenses

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
            category = "Comida",
            amount = "145",
            icon = Icons.Default.Fastfood,
            style = ExpenseRowStyle.CARD
        )
        ExpenseRow(
            nombre = "CFE - Luz",
            category = "Servicios",
            amount = "380",
            icon = Icons.Default.Lightbulb,
            style = ExpenseRowStyle.CARD
        )
    }
}