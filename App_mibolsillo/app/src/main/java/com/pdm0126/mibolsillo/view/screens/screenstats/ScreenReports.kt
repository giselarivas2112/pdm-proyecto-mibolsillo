package com.pdm0126.mibolsillo.view.screens.screenstats

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import ir.ehsannarmani.compose_charts.models.PopupProperties
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.utils.getNombreMes
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import com.pdm0126.mibolsillo.view.screenspecific.MonthSelectorComponent
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
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
fun ScreenReports(
    navigationBack: () -> Unit,
    viewModel: ReportsViewModel = viewModel(),

    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToPerfil: () -> Unit,

    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navegationToFixedPayment: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val summary by viewModel.summary.collectAsState()
    val distribution by viewModel.distribution.collectAsState()
    val dailyExpenses by viewModel.dailyExpenses.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val mes by viewModel.mes.collectAsState()
    val anio by viewModel.anio.collectAsState()

    LaunchedEffect(mes, anio) {
        viewModel.loadData()
    }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "Reportes",
                onInicioClick = { navigationToDashboard() },
                onMisGastosClick = { navigationToExpenses() },
                onReportesClick = { /* ya estamos aquí */ },
                onPerfilClick = { navigationToPerfil() },
                expanded = expanded,
                onFabClick = { expanded = !expanded }
            )
        },

        floatingActionButton = {
            FabExpandedMenu(
                visible = expanded,
                onCategory = { expanded = false; navegationToCategory() },
                onBudget = { expanded = false; navegationToBudget() },
                onFixedPayment = { expanded = false; navegationToFixedPayment() },
                onExpense = { expanded = false; navigationToExpense() }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            // HEADER + SELECTOR DE MES
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    HeaderSection()

                    MonthSelectorComponent(
                        navigationBack = navigationBack,
                        titulo = "Reportes",
                        mesActual = "${getNombreMes(mes)} $anio",
                        onAnteriorMes = { viewModel.mesAnterior() },
                        onSiguienteMes = { viewModel.mesSiguiente() }
                    )
                }
            }

            // ESTADO DE CARGA / ERROR
            item {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    error != null -> {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            // TARJETAS: TOTAL GASTADO / PRESUPUESTO / DISPONIBLE
            if (!isLoading && error == null) {
                item {
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            modifier = Modifier.weight(1f),
                            label = "Total gastado",
                            value = "$${"%.2f".format(summary?.totalGastado ?: 0.0)}",
                            valueColor = Color(0xFFEF5350)
                        )
                        StatCard(
                            modifier = Modifier.weight(1f),
                            label = "Presupuesto",
                            value = "$${"%.2f".format(summary?.totalPresupuestado ?: 0.0)}",
                            valueColor = Color(0xFF8A2BE2)
                        )
                        StatCard(
                            modifier = Modifier.weight(1f),
                            label = "Disponible",
                            value = "$${"%.2f".format(summary?.totalDisponible ?: 0.0)}",
                            valueColor = Color(0xFF4DB6AC)
                        )
                    }
                }

                // ¿EN QUÉ GASTAS MÁS? -> DONA + LEYENDA (todas las categorías, sin agrupar)
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "¿En qué gastas más?",
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )

                    Card(
                        modifier = Modifier
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

                                // Leyenda vertical: escala bien sin importar cuántas categorías sean
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

                // GASTO DIARIO DEL MES -> BARRAS

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Gasto diario del mes",
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )

                    Card(
                        modifier = Modifier
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
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                // PROGRESO DE PRESUPUESTOS
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Progreso de presupuestos",
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        val categorias = summary?.categorias ?: emptyList()

                        if (categorias.isEmpty()) {
                            Text(
                                text = "No tienes presupuestos este mes",
                                modifier = Modifier.padding(16.dp),
                                color = Color.Gray
                            )
                        } else {
                            Column(modifier = Modifier.padding(16.dp)) {
                                categorias.forEach { categoria ->
                                    val color = when (categoria.estado) {
                                        "excedido" -> Color(0xFFEF5350)
                                        "cerca_del_limite" -> Color(0xFFFF9800)
                                        else -> Color(0xFF4DB6AC)
                                    }
                                    BudgetProgressRow(
                                        nombre = categoria.categoriaNombre,
                                        porcentaje = categoria.porcentajeUsado.toInt(),
                                        color = color
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    valueColor: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, fontSize = 11.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = valueColor)
        }
    }
}

@Composable
private fun BudgetProgressRow(nombre: String, porcentaje: Int, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nombre,
                fontSize = 13.sp,
                color = Color(0xFF333333),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            Text(text = "$porcentaje%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { (porcentaje / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color(0xFFE0E0E0)
        )
    }
}