package com.pdm0126.mibolsillo.view.components.forms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdm0126.mibolsillo.data.model.Category

@Composable
fun CategoryDropdownLoader(
    categories: List<Category>,
    loading: Boolean,
    error: String?,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit,
    onRetry: () -> Unit
) {
    if (loading) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF8A2BE2))
        }
    } else if (error != null) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("No se pudieron cargar las categorías", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2))
            ) {
                Text("Reintentar")
            }
        }
    } else {
        _root_ide_package_.com.pdm0126.mibolsillo.view.components.forms.CategorySection(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )
    }
}