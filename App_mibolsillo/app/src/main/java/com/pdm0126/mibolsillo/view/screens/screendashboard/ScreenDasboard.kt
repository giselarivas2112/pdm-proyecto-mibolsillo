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
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.screenspecific.RecentExpensesSection
import com.pdm0126.mibolsillo.view.screenspecific.SummaryCardsSection

@Composable
fun ScreenDashboard(
    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navegationToFixedPayment: () -> Unit,
    navigationToPerfil: ()-> Unit,
    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

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
        }
    ) { padding ->

        Box(
            modifier = Modifier.fillMaxSize()
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
                                text = "Pablito Rivas",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    SummaryCardsSection()
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    RecentExpensesSection()
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

                _root_ide_package_.com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu(
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