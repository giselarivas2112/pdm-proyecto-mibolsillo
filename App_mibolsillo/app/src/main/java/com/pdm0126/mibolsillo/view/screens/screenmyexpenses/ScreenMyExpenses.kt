package com.pdm0126.mibolsillo.view.screens.screenmyexpenses

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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import com.pdm0126.mibolsillo.view.components.common.RowCard
import com.pdm0126.mibolsillo.utils.getNombreMes
import com.pdm0126.mibolsillo.view.specificcomponents.myexpenses.CategoryFilterRow
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import com.pdm0126.mibolsillo.view.components.navigation.MonthSelectorComponent
import com.pdm0126.mibolsillo.view.specificcomponents.myexpenses.StatsCardComponent
import kotlin.Unit

@Composable
fun ScreenMyExpenses( navigationBack: () -> Unit,
    navegationToFixedPayment: () -> Unit,
    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToPerfil: ()-> Unit,
    navigationToViewReports: () -> Unit,
    viewModel: MyExpensesViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val expenses by viewModel.filteredExpenses.collectAsState()
    val expensesGrouped by viewModel.expensesGroupedByDate.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val mes by viewModel.mes.collectAsState()
    val anio by viewModel.anio.collectAsState()
    val categoriaFiltro by viewModel.categoriaFiltro.collectAsState()
    val totalGastado by viewModel.totalGastado.collectAsState()
    val mayorGasto by viewModel.mayorGasto.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()


    LaunchedEffect(mes, anio) {
        viewModel.loadData()
    }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "Mis gastos",
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
        },

        floatingActionButton = {
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
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->

        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = { viewModel.loadData(isRefresh = true) },
            modifier = Modifier
                .fillMaxSize()
        ){

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    HeaderSection()

                    MonthSelectorComponent(
                        navigationBack = navigationBack,
                        titulo = "Mis gastos",
                        mesActual = "${getNombreMes(mes)} $anio",
                        onAnteriorMes = { viewModel.mesAnterior() },
                        onSiguienteMes = { viewModel.mesSiguiente() }
                    )

                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                StatsCardComponent(
                    gastado = "$${"%.2f".format(totalGastado)}",
                    mayorGasto = "$${"%.2f".format(mayorGasto)}"
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                CategoryFilterRow(
                    categorias = listOf("Todos") + categories.map { it.nombre },
                    categoriaSeleccionada = categoriaFiltro,
                    onCategoriaClick = { viewModel.setCategoriaFiltro(it) }
                )
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF8A2BE2))
                    }
                }
            } else if (expensesGrouped.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay gastos para este mes",
                            color = Color(0xFF8A2BE2),
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                expensesGrouped.forEach { (fecha, gastosDelDia) ->
                    item(key = fecha) {
                        Text(
                            text = fecha,
                            color = Color(0xFF8A2BE2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(
                        items = gastosDelDia,
                        key = { it.id }
                    ) { expense ->
                        RowCard(
                            icon = Icons.Default.AttachMoney,
                            iconColor = Color(0xFF8A2BE2),
                            iconBgColor = Color(0xFFF3E5F5),
                            title = expense.descripcion ?: expense.categoryName ?: "",
                            subtitle = expense.categoryName ?: "Sin categoría",
                            monto = "-$${"%.2f".format(expense.monto)}",
                            montoColor = Color(0xFFD32F2F)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}