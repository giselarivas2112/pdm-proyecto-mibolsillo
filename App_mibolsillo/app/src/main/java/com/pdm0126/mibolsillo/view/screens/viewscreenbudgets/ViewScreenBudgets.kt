package com.pdm0126.mibolsillo.view.screens.viewscreenbudgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.newcomponents.BudgetCategoryRowCard
import com.pdm0126.mibolsillo.newcomponents.MonthlyBudgetSummaryCard
import com.pdm0126.mibolsillo.utils.getNombreMes
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import com.pdm0126.mibolsillo.view.screenspecific.MonthSelectorComponent

@Composable
fun ScreenViewBudgets(navigationBack: () -> Unit,
    viewModel: BudgetViewModel = viewModel(),

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
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val mes by viewModel.mes.collectAsState()
    val anio by viewModel.anio.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "Mis presupuestos",
                onInicioClick = {
                    navigationToDashboard()
                },
                onMisGastosClick = {
                    navigationToExpenses()
                },
                onReportesClick = {
                    // Navegación a reportes en el futuro
                },
                onPerfilClick = {
                    navigationToPerfil()
                },
                expanded = expanded,
                onFabClick = { expanded = !expanded }
            )
        },

        floatingActionButton = {
            FabExpandedMenu(
                visible = expanded,
                onCategory = {
                    expanded = false
                    navegationToCategory()
                },
                onBudget = {
                    expanded = false
                    navegationToBudget()
                },
                onFixedPayment = {
                    expanded = false
                    navegationToFixedPayment()
                },
                onExpense = {
                    expanded = false
                    navigationToExpense()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    HeaderSection()

                    MonthSelectorComponent(
                        navigationBack = navigationBack,
                        titulo = "Mis presupuestos",
                        mesActual = "${getNombreMes(mes)} $anio",
                        onAnteriorMes = { viewModel.mesAnterior() },
                        onSiguienteMes = { viewModel.mesSiguiente() }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    MonthlyBudgetSummaryCard(
                        totalBudget = "$${summary?.totalPresupuestado ?: 0.0}",
                        spent = "$${summary?.totalGastado ?: 0.0}",
                        available = "$${summary?.totalDisponible ?: 0.0}",
                        percentageUsed = (summary?.porcentajeGlobal ?: 0.0).toInt()
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "CATEGORÍAS (${summary?.categorias?.size ?: 0})",
                    color = Color(0xFF8A2BE2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
            }

            item {
                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    error != null -> {
                        Text(text = "Error: $error", color = Color.Red,
                            modifier = Modifier.padding(16.dp))
                    }
                }
            }

            items(summary?.categorias ?: emptyList()) { categoria ->
                val isAlert = categoria.estado == "cerca_del_limite" || categoria.estado == "excedido"
                val statusText = when (categoria.estado) {
                    "excedido" -> "Excedido"
                    "cerca_del_limite" -> "Cerca del límite"
                    else -> "En control"
                }
                val statusColor = when (categoria.estado) {
                    "excedido" -> Color(0xFFEF5350)
                    "cerca_del_limite" -> Color(0xFFFF9800)
                    else -> Color(0xFF4DB6AC)
                }

                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    BudgetCategoryRowCard(
                        icon = Icons.Default.AttachMoney,
                        iconBgColor = Color(0xFFF3E5F5),
                        iconColor = Color(0xFF8A2BE2),
                        categoryName = categoria.categoriaNombre,
                        statusText = statusText,
                        statusColor = statusColor,
                        isAlert = isAlert,
                        amountProgress = "$${categoria.gastado} / $${categoria.limite}",
                        remainingText = "Quedan $${categoria.disponible}",
                        remainingColor = Color.Gray,
                        percentage = categoria.porcentajeUsado.toInt(),
                        progressBarColor = statusColor,
                        limitLabel = "límite: $${categoria.limite}"
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}