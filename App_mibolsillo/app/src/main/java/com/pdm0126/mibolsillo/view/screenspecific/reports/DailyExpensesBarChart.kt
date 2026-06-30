package com.pdm0126.mibolsillo.view.screenspecific.reports

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.mibolsillo.model.DailyExpenses
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun DailyExpensesBarChart(
    dailyExpenses: DailyExpenses?,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Gasto diario del mes",
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
        val dias = dailyExpenses?.dias ?: emptyList()

        if (dias.isEmpty()) {
            Text(
                text = "Sin datos diarios este mes",
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        } else {
            val barWidth = 10.dp
            val barSpacing = 6.dp
            val anchoTotal = (barWidth + barSpacing) * dias.size

            val barsData = dias.map { dia ->
                Bars(
                    label = "",
                    values = listOf(
                        Bars.Data(
                            value = dia.total,
                            color = SolidColor(Color(0xFF8A2BE2))
                        )
                    )
                )
            }

            val scrollState = rememberScrollState()
            val density = LocalDensity.current

            LaunchedEffect(dias) {
                val primerDiaConGasto = dias.indexOfFirst { it.total > 0 }
                if (primerDiaConGasto > 0) {
                    val pxPorBarra = with(density) { (barWidth + barSpacing).toPx() }
                    val offset = (pxPorBarra * (primerDiaConGasto - 1)).coerceAtLeast(0f)
                    scrollState.scrollTo(offset.toInt())
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(12.dp)
            ) {
                ColumnChart(
                    modifier = Modifier
                        .width(anchoTotal)
                        .height(160.dp),
                    data = barsData,
                    barProperties = BarProperties(
                        spacing = barSpacing,
                        thickness = barWidth
                    ),
                    gridProperties = GridProperties(enabled = false),
                    indicatorProperties = HorizontalIndicatorProperties(enabled = false),
                    labelHelperProperties = LabelHelperProperties(enabled = false),
                    popupProperties = PopupProperties(
                        enabled = true,
                        containerColor = Color(0xFF8A2BE2),
                        cornerRadius = 8.dp,
                        contentHorizontalPadding = 8.dp,
                        contentVerticalPadding = 6.dp,
                        duration = 2000L,
                        contentBuilder = { popup ->
                            val dia = dias.getOrNull(popup.dataIndex)?.dia ?: (popup.dataIndex + 1)
                            "Día $dia: $${"%.2f".format(popup.value)}"
                        }
                    )
                )
            }

            Text(
                text = "↔  Desliza para ver todos los días",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}