package com.pdm0126.mibolsillo.view.specificcomponents.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.mibolsillo.data.model.BudgetCategoryResume

@Composable
fun BudgetProgressSection(
    categorias: List<BudgetCategoryResume>,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Progreso de presupuestos",
        color = Color(0xFF333333),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 18.dp)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        if (categorias.isEmpty()) {
            Text(
                text = "No tienes presupuestos este mes",
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                categorias.forEach { categoria ->
                    val color = when (categoria.estado) {
                        "excedido" -> Color(0xFFEF5350)
                        "cerca_del_limite" -> Color(0xFFFF9800)
                        else -> Color(0xFF4DB6AC)
                    }
                    BudgetProgressRow(
                        nombre = categoria.categoriaNombre,
                        porcentaje = categoria.porcentajeUsado.toInt(),
                        color = color
                    )
                }
            }
        }
    }
}

@Composable
private fun BudgetProgressRow(nombre: String, porcentaje: Int, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nombre,
                fontSize = 13.sp,
                color = Color(0xFF333333),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            Text(text = "$porcentaje%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { (porcentaje / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color(0xFFE0E0E0)
        )
    }
}