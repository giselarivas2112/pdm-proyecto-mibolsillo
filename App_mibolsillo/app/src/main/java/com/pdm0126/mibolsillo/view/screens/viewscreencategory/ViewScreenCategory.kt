package com.pdm0126.mibolsillo.view.screens.viewscreencategory

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.pdm0126.mibolsillo.newcomponents.CategoryFilterChip
import com.pdm0126.mibolsillo.newcomponents.CategoryRowCard
import com.pdm0126.mibolsillo.newcomponents.CategorySearchInputField
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar

@Composable
fun ScreenViewCategory(
    navigationBack: () -> Unit,

    totalCategorias: String = "8",

    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToPerfil: () -> Unit,

    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navegationToFixedPayment: () -> Unit,

) {
    var expanded by remember { mutableStateOf(false) }
    var searchInput by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Todas") }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "Categorías",
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = navigationBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Mis categorías",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "$totalCategorias categorías creadas",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 15.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CategorySearchInputField(
                        value = searchInput,
                        onValueChange = { searchInput = it }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CategoryFilterChip(
                            text = "Todas",
                            isActive = selectedFilter == "Todas",
                            onClick = { selectedFilter = "Todas" }
                        )
                    }
                    item {
                        CategoryFilterChip(
                            text = "Recientes",
                            isActive = selectedFilter == "Recientes",
                            onClick = { selectedFilter = "Recientes" }
                        )
                    }
                    item {
                        CategoryFilterChip(
                            text = "Con Gastos",
                            isActive = selectedFilter == "Con Gastos",
                            onClick = { selectedFilter = "Con Gastos" }
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CategoryRowCard(
                        emojiIcon = "🍔",
                        iconBgColor = Color(0x268A2BE2),
                        title = "Comida",
                        subtitle = "12 gastos",
                    )

                    CategoryRowCard(
                        emojiIcon = "🚌",
                        iconBgColor = Color(0x26B39DDB),
                        title = "Transporte",
                        subtitle = "8 gastos",
                    )


                    CategoryRowCard(
                        emojiIcon = "🏠",
                        iconBgColor = Color(0x265C6BC0),
                        title = "Renta",
                        subtitle = "",
                    )

                    CategoryRowCard(
                        emojiIcon = "⚡",
                        iconBgColor = Color(0x26EF5350),
                        title = "Servicios",
                        subtitle = "",
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}