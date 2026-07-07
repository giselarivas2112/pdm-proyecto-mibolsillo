package com.pdm0126.mibolsillo.view.specificcomponents.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdm0126.mibolsillo.view.components.stats.StatCard

@Composable
fun StatsSummaryCards(
    totalGastado: Double,
    totalPresupuestado: Double,
    totalDisponible: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            title = "Total gastado",
            value = "$${"%.2f".format(totalGastado)}"
        )
        StatCard(
            modifier = Modifier.weight(1f),
            title = "Presupuesto",
            value = "$${"%.2f".format(totalPresupuestado)}"
        )
        StatCard(
            modifier = Modifier.weight(1f),
            title = "Disponible",
            value = "$${"%.2f".format(totalDisponible)}"
        )
    }
}

