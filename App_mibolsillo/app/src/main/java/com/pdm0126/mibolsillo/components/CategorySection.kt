package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "Comida",
        "Transporte",
        "Renta",
        "Servicios",
        "Ocio",
        "Compras"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "CATEGORÍA",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A2BE2)
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFD7B7F9)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 18.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = selectedCategory.ifEmpty { "Selecciona una categoría" },
                        color = if (selectedCategory.isEmpty()) Color.Gray else Color.Unspecified,
                        modifier = Modifier.weight(1f)
                    )

                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}
