package com.pdm0126.mibolsillo.view.specificcomponents.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.mibolsillo.data.model.Expense
import com.pdm0126.mibolsillo.view.components.common.RowCard

@Composable
fun RecentExpensesSection(
    expenses: List<Expense>
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Gastos recientes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (expenses.isEmpty()) {
                Text(
                    text = "No hay gastos recientes este mes",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                expenses.forEach { expense ->
                    RowCard(modifier = Modifier,
                        icon = Icons.Default.AttachMoney,
                        iconColor = Color(0xFF8A2BE2),
                        iconBgColor = Color(0xFFEDE7F6),
                        title = expense.categoryName ?: "Sin categoría",
                        subtitle = expense.fecha,
                        monto = "-$${"%.2f".format(expense.monto)}",
                        montoColor = Color(0xFFD32F2F),
                        showCard = false
                    )
                }
            }
        }
    }
}