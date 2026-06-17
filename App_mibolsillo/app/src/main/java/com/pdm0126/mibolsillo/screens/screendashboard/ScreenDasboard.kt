package com.pdm0126.mibolsillo.screens.screendashboard

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
import com.pdm0126.mibolsillo.components.HomeBottomBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pdm0126.mibolsillo.components.FabExpandedMenu
import com.pdm0126.mibolsillo.components.HeaderSection
import com.pdm0126.mibolsillo.components.RecentExpensesSection
import com.pdm0126.mibolsillo.components.SummaryCardsSection

@Composable
fun ScreenDashboard(navigationToLogin: () -> Unit,navigationToExpense: () -> Unit,
                    navegationToBudget: () -> Unit, navegationToCategory: () -> Unit,
                    navegationToFixedPayment: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            HomeBottomBar(
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

                        IconButton(
                            onClick = { navigationToLogin() },
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    top = 55.dp
                                )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }

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