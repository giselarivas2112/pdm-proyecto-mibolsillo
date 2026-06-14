package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecentExpensesSection() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Gastos recientes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExpenseRow(
                icon = Icons.Default.Fastfood,
                category = "Comida",
                date = "12 Jun 2026",
                amount = "$25.00"
            )

            ExpenseRow(
                icon = Icons.Default.DirectionsBus,
                category = "Transporte",
                date = "11 Jun 2026",
                amount = "$10.00"
            )

            ExpenseRow(
                icon = Icons.Default.Movie,
                category = "Entretenimiento",
                date = "10 Jun 2026",
                amount = "$18.00"
            )
        }
    }
}