package com.pdm0126.mibolsillo.view.screens.screendashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.specificcomponents.dashboard.RecentExpensesSection
import com.pdm0126.mibolsillo.view.specificcomponents.dashboard.SummaryCardsSection


@Composable
fun ScreenDashboard(
    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navegationToFixedPayment: () -> Unit,
    navigationToPerfil: () -> Unit,
    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToViewReports: () -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {

    var expanded by remember { mutableStateOf(false) }
    val nombreUsuario by viewModel.nombreUsuario.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val recentExpenses by viewModel.recentExpenses.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "inicio",
                onInicioClick = {
                    navigationToDashboard()
                },
                onMisGastosClick = {
                    navigationToExpenses()
                },
                onReportesClick = {
                    navigationToViewReports()
                },
                onPerfilClick = {
                    navigationToPerfil()
                },
                expanded = expanded,

                onFabClick = {
                    expanded = !expanded
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier.fillMaxSize()
        )
        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = { viewModel.loadData(isRefresh = true) },
            modifier = Modifier
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {

                    Box {

                        HeaderSection()

                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 24.dp,
                                    top = 120.dp
                                )
                        ) {

                            Text(
                                text = "Hola,",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 18.sp
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = nombreUsuario.ifEmpty { "Usuario" },
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    when {
                        isLoading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color(0xFF8A2BE2))
                            }
                        }

                        error != null -> {
                            Text(
                                text = "Error: $error",
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        else -> {
                            SummaryCardsSection(
                                totalGastado = "$${"%.2f".format(summary?.totalGastado ?: 0.0)}",
                                totalDisponible = "$${"%.2f".format(summary?.totalDisponible ?: 0.0)}"
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    RecentExpensesSection(
                        expenses = recentExpenses
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp)
            ) {

                FabExpandedMenu(
                    visible = expanded,
                    onCategory = {
                        navegationToCategory()
                    },
                    onBudget = {
                        navegationToBudget()
                    },
                    onFixedPayment = {
                        navegationToFixedPayment()
                    },
                    onExpense = {
                        navigationToExpense()
                    }
                )
            }
        }
    }
}