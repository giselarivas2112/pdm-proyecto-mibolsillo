package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecordatorioPrevioSection(
    diaAviso: String = "17",
    diasAntes: String = "3"
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "RECORDATORIO PREVIO",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A2BE2)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)) // Amarillo claro
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color(0xFFFBC02D)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Recibirás un aviso el día $diaAviso de cada mes.",
                        fontSize = 13.
                        sp,
                        color = Color.Black
                    )
                    Text(
                        text = "$diasAntes días antes de que venza tu pago.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}