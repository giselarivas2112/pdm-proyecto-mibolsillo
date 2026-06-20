package com.pdm0126.mibolsillo.view.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionSection(description: String, onDescriptionChange: (String) -> Unit) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Text(
            text = "DESCRIPCIÓN",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A2BE2)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = {
                Text("Agrega una nota")
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}