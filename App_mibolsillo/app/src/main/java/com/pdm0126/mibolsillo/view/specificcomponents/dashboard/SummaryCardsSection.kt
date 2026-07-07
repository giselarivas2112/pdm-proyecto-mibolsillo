package com.pdm0126.mibolsillo.view.specificcomponents.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun SummaryCardsSection(
    totalGastado: String,
    totalDisponible: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Total gastado")

                Text(
                    totalGastado,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("este mes")
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Saldo disponible")

                Text(
                    totalDisponible,
                    color = Color(0xFF00B894),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("para gastar")
            }
        }
    }
}