package com.pdm0126.mibolsillo.view.screenspecific

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryFilterRow(
    categoriaSeleccionada: String = "Todos",
    onCategoriaClick: (String) -> Unit
) {
    val categorias = listOf("Todos", "Comida", "Transporte", "Servicios", "Ocio")

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categorias) { categoria ->
            val esSeleccionado = categoria == categoriaSeleccionada

            Card(
                modifier = Modifier.clickable { onCategoriaClick(categoria) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (esSeleccionado) Color(0xFF8A2BE2) else Color.White
                ),
                border = if (!esSeleccionado) BorderStroke(1.dp, Color(0xFFD7B7F9)) else null
            ) {
                Text(
                    text = categoria,
                    color = if (esSeleccionado) Color.White else Color(0xFF8A2BE2),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}