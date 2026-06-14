package com.pdm0126.mibolsillo.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SaveExpenseButton(onSave: () -> Unit = {}) {

    val context = LocalContext.current

    Button(
        onClick = {
            Toast.makeText(context, "Gasto guardado exitosamente", Toast.LENGTH_SHORT).show()
            onSave()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(60.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF8A2BE2)
        )
    ){

        Text(
            text = "Guardar gasto",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}