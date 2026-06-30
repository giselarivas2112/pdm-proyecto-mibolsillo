package com.pdm0126.mibolsillo.view.screens.screenstats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
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
import ir.ehsannarmani.compose_charts.models.Pie

// Paleta fija para asignar un color a cada categoría (el backend no manda color)
private val chartColors = listOf(
    Color(0xFF8A2BE2), // morado (color principal de la app)
    Color(0xFFBA68C8),
    Color(0xFFFFA726),
    Color(0xFF4DB6AC),
    Color(0xFF64B5F6),
    Color(0xFFB0BEC5)
)

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

                // ¿EN QUÉ GASTAS MÁS? -> DONA + LEYENDA
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
                            val pieData = items.mapIndexed { index, item ->
                                Pie(
                                    label = item.nombre,
                                    data = item.porcentaje,
                                    color = chartColors[index % chartColors.size],
                                    selectedColor = chartColors[index % chartColors.size]
                                )
                            }

                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    PieChart(
                                        modifier = Modifier.size(150.dp),
                                        data = pieData,
                                        style = Pie.Style.Stroke(width = 34f)
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "Total", fontSize = 11.sp, color = Color.Gray)
                                        Text(
                                            text = "$${"%.2f".format(distribution?.totalGastado ?: 0.0)}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(0.dp))

                                Column(modifier = Modifier.padding(start = 16.dp)) {
                                    items.forEachIndexed { index, item ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(vertical = 3.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .clip(CircleShape)
                                                    .background(chartColors[index % chartColors.size])
                                            )
                                            Spacer(modifier = Modifier.height(0.dp))
                                            Text(
                                                text = "  ${item.nombre}",
                                                fontSize = 12.sp,
                                                color = Color(0xFF333333)
                                            )
                                            Text(
                                                text = "  ${item.porcentaje.toInt()}%",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF8A2BE2)
                                            )
                                        }
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

                            ColumnChart(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .padding(12.dp),
                                data = barsData,
                                barProperties = BarProperties(
                                    spacing = 1.dp,
                                    thickness = 6.dp
                                )
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
            Text(text = nombre, fontSize = 13.sp, color = Color(0xFF333333))
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