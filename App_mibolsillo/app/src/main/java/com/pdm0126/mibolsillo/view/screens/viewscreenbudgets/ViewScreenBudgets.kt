package com.pdm0126.mibolsillo.view.screens.viewscreenbudgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FlashOn
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
import com.pdm0126.mibolsillo.newcomponents.BudgetCategoryRowCard
import com.pdm0126.mibolsillo.newcomponents.MonthlyBudgetSummaryCard
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import com.pdm0126.mibolsillo.view.screenspecific.MonthSelectorComponent

@Composable
fun ScreenViewBudgets(navigationBack: () -> Unit,

    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToPerfil: () -> Unit,

    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navegationToFixedPayment: () -> Unit,


) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "Mis preasupuestos",
                onInicioClick = { navigationToDashboard() },
                onMisGastosClick = { navigationToExpenses() },
                onReportesClick = { /* Futuro a reportes */ },
                onPerfilClick = { navigationToPerfil() },
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
                        mesActual = "Mayo 2026",
                        onAnteriorMes = { },
                        onSiguienteMes = { }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    MonthlyBudgetSummaryCard(
                        totalBudget = "$12,500.00",
                        spent = "$7,400",
                        available = "$5,100",
                        percentageUsed = 59
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "CATEGORÍAS (6)",
                    color = Color(0xFF8A2BE2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    BudgetCategoryRowCard(
                        icon = Icons.Default.Fastfood,
                        iconBgColor = Color(0xFFFFF3E0),
                        iconColor = Color(0xFFFF9800),
                        categoryName = "Comida",
                        statusText = "Cerca del límite",
                        statusColor = Color(0xFFFF9800),
                        isAlert = true,
                        amountProgress = "$1,700 / $2,000",
                        remainingText = "Quedan $300",
                        remainingColor = Color.Gray,
                        percentage = 85,
                        progressBarColor = Color(0xFFFF9800),
                        limitLabel = "límite: $2,000"
                    )

                    BudgetCategoryRowCard(
                        icon = Icons.Default.Commute,
                        iconBgColor = Color(0xFFF3E5F5),
                        iconColor = Color(0xFF8A2BE2),
                        categoryName = "Transporte",
                        statusText = "En control",
                        statusColor = Color(0xFF4DB6AC),
                        isAlert = false,
                        amountProgress = "$900 / $2,000",
                        remainingText = "Quedan $1,100",
                        remainingColor = Color.Gray,
                        percentage = 45,
                        progressBarColor = Color(0xFF8A2BE2),
                        limitLabel = "límite: $2,000"
                    )

                    BudgetCategoryRowCard(
                        icon = Icons.Default.FlashOn,
                        iconBgColor = Color(0xFFE0F2F1),
                        iconColor = Color(0xFF009688),
                        categoryName = "Servicios",
                        statusText = "En control",
                        statusColor = Color(0xFF4DB6AC),
                        isAlert = false,
                        amountProgress = "$1,200 / $2,000",
                        remainingText = "Quedan $800",
                        remainingColor = Color.Gray,
                        percentage = 60,
                        progressBarColor = Color(0xFF4DB6AC),
                        limitLabel = "límite: $2,000"
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}