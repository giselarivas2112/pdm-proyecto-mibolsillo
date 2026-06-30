package com.pdm0126.mibolsillo.view.screenspecific.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.mibolsillo.model.Distribution
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Pie

private fun generarColores(cantidad: Int): List<Color> {
    if (cantidad <= 0) return emptyList()
    val huePrincipal = 271f
    return List(cantidad) { index ->
        val hue = (huePrincipal + index * (360f / cantidad)) % 360f
        Color.hsv(hue = hue, saturation = 0.55f, value = 0.85f)
    }
}

@Composable
fun ExpenseDistributionChart(
    distribution: Distribution?,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "¿En qué gastas más?",
        color = Color(0xFF333333),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 18.dp)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        val items = distribution?.distribucion ?: emptyList()

        if (items.isEmpty()) {
            Text(
                text = "Sin gastos registrados este mes",
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        } else {
            val colores = generarColores(items.size)
            val pieData = items.mapIndexed { index, item ->
                Pie(
                    label = item.nombre,
                    data = item.porcentaje,
                    color = colores[index],
                    selectedColor = colores[index]
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    PieChart(
                        modifier = Modifier.size(170.dp),
                        data = pieData,
                        style = Pie.Style.Stroke(width = 34.dp),
                        labelHelperProperties = LabelHelperProperties(enabled = false)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Total", fontSize = 11.sp, color = Color.Gray)
                        Text(
                            text = "$${"%.2f".format(distribution?.totalGastado ?: 0.0)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(colores[index])
                        )
                        Text(
                            text = item.nombre,
                            fontSize = 13.sp,
                            color = Color(0xFF333333),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        )
                        Text(
                            text = "${item.porcentaje.toInt()}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = colores[index]
                        )
                    }
                }
            }
        }
    }
}