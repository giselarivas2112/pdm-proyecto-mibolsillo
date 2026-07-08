package com.pdm0126.mibolsillo.view.specificcomponents.registerbudget

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun AlertSliderSection(
    sliderValue: Float,
    onValueChange: (Float) -> Unit,
    totalBudget: String
) {
    val totalNum = totalBudget.toDoubleOrNull() ?: 0.0
    val dineroAlerta = totalNum * sliderValue
    val porcentajeTexto = "${(sliderValue * 100).toInt()}%"

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Text(
            text = "ALERTA CUANDO ALCANCE",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A2BE2),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFD7B7F9)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Slider(
                    value = sliderValue,
                    onValueChange = { nuevoValor ->
                        onValueChange(
                            when {
                                nuevoValor < 0.65f -> 0.5f
                                nuevoValor < 0.90f -> 0.8f
                                else -> 1.0f
                            }
                        )
                    },
                    valueRange = 0.5f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF8A2BE2),
                        activeTrackColor = Color(0xFF8A2BE2),
                        inactiveTrackColor = Color(0xFFEFE5FD)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "50%", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "80%", fontSize = 12.sp, color = Color(0xFF8A2BE2), fontWeight = FontWeight.Bold)
                    Text(text = "100%", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Text(
                text = "Te avisaremos cuando tus gastos de este mes alcancen el $porcentajeTexto de tu presupuesto (es decir, $${String.format(Locale.US, "%.2f", dineroAlerta)}).",
                color = Color(0xFFE65100),
                fontSize = 13.sp,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}