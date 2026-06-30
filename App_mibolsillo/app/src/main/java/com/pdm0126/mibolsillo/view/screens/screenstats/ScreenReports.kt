package com.pdm0126.mibolsillo.view.screens.screenstats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.utils.getNombreMes
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import com.pdm0126.mibolsillo.view.screenspecific.MonthSelectorComponent
import com.pdm0126.mibolsillo.view.screenspecific.reports.BudgetProgressSection
import com.pdm0126.mibolsillo.view.screenspecific.reports.DailyExpensesBarChart
import com.pdm0126.mibolsillo.view.screenspecific.reports.ExpenseDistributionChart
import com.pdm0126.mibolsillo.view.screenspecific.reports.StatsSummaryCards


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

        LazyColumn(modifier = Modifier.fillMaxSize()) {

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

            if (!isLoading && error == null) {
                item {
                    Spacer(modifier = Modifier.height(14.dp))
                    StatsSummaryCards(
                        totalGastado = summary?.totalGastado ?: 0.0,
                        totalPresupuestado = summary?.totalPresupuestado ?: 0.0,
                        totalDisponible = summary?.totalDisponible ?: 0.0
                    )
                }

                item {
                    ExpenseDistributionChart(distribution = distribution)
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    DailyExpensesBarChart(dailyExpenses = dailyExpenses)
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    BudgetProgressSection(categorias = summary?.categorias ?: emptyList())
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}