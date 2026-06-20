package com.pdm0126.mibolsillo.view.components.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpensesSectionYesterday() {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "AYER, 9 MAYO",
            color = Color(0xFF8A2BE2),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        _root_ide_package_.com.pdm0126.mibolsillo.view.components.expenses.ExpenseRow(
            nombre = "Walmart",
            category = "Compras",
            amount = "520",
            icon = Icons.Default.ShoppingCart,
            style = _root_ide_package_.com.pdm0126.mibolsillo.view.components.expenses.ExpenseRowStyle.CARD
        )
    }
}