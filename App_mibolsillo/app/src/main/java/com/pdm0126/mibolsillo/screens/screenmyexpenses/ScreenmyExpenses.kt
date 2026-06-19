package com.pdm0126.mibolsillo.screens.screenmyexpenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.pdm0126.mibolsillo.components.CategoryFilterRow
import com.pdm0126.mibolsillo.components.ExpenseRow
import com.pdm0126.mibolsillo.components.ExpensesSectionYesterday
import com.pdm0126.mibolsillo.components.ExpensesTodaySection
import com.pdm0126.mibolsillo.components.FabExpandedMenu
import com.pdm0126.mibolsillo.components.HeaderSection
import com.pdm0126.mibolsillo.components.HomeBottomBar
import com.pdm0126.mibolsillo.components.MonthSelectorComponent
import com.pdm0126.mibolsillo.components.StatsCardComponent

@Composable
fun ScreenMyExpenses( navigationBack: () -> Unit,
    navegationToFixedPayment: () -> Unit,
    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToPerfil: ()-> Unit
) {
    var categoriaFiltro by remember { mutableStateOf("Todos") }
    var expanded by remember { mutableStateOf(false) }

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
                    // navegar a Reportes
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
                        mesActual = "Mayo 2026",
                        onAnteriorMes = { },
                        onSiguienteMes = { }
                    )

                }
            }

            item {

                Spacer(modifier = Modifier.height(16.dp))

                StatsCardComponent(
                    gastado = "$7,400",
                    transacciones = "24",
                    mayorGasto = "$2,800"
                )
            }

            item {

                Spacer(modifier = Modifier.height(16.dp))

                CategoryFilterRow(
                    categoriaSeleccionada = categoriaFiltro,
                    onCategoriaClick = {
                        categoriaFiltro = it
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                ExpensesTodaySection()
            }

            item {
                ExpensesSectionYesterday()
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}