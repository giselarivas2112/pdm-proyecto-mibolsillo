package com.pdm0126.mibolsillo.view.screenspecific

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdm0126.mibolsillo.view.components.stats.StatCard

@Composable
fun ProfileStatsRow(
    gastosTotales: String,
    totalCategorias: String,
    totalPresupuestos: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            title = "Gastos totales",
            value = gastosTotales,
            modifier = Modifier.weight(1f)
        )
      StatCard(
            title = "Categorías",
            value = totalCategorias,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Presupuestos",
            value = totalPresupuestos,
            modifier = Modifier.weight(1f)
        )
    }
}

