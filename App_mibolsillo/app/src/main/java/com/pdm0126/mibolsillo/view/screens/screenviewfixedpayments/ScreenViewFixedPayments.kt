package com.pdm0126.mibolsillo.view.screens.screenviewfixedpayments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.view.specificcomponents.viewfixedpayments.FixedPaymentTotalBanner
import com.pdm0126.mibolsillo.view.components.common.RowCard
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.navigation.FabExpandedMenu
import com.pdm0126.mibolsillo.view.components.navigation.HomeBottomBar
import kotlin.Unit

@Composable
fun ScreenViewFixedPayments(
    navigationBack: () -> Unit,
    viewModel: FixedPaymentViewModel = viewModel(),

    navigationToDashboard: () -> Unit,
    navigationToExpenses: () -> Unit,
    navigationToPerfil: () -> Unit,
    navigationToViewReports: () -> Unit,

    navigationToExpense: () -> Unit,
    navegationToBudget: () -> Unit,
    navegationToCategory: () -> Unit,
    navegationToFixedPayment: () -> Unit,

    ) {
    var expanded by remember { mutableStateOf(false) }
    val fixedPayments by viewModel.fixedPayments.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()
    val total = fixedPayments.sumOf { it.monto }


    LaunchedEffect(Unit) {
        viewModel.getFixedPayments()
    }

    Scaffold(
        containerColor = Color(0xFFF7F9FC),

        bottomBar = {
            HomeBottomBar(
                pantallaActual = "Pagos fijos",
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

        PullToRefreshBox(
            isRefreshing = refreshing,
            onRefresh = { viewModel.getFixedPayments(isRefresh = true) },
            modifier = Modifier
                .fillMaxSize()
        ){
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
                        Text(
                            text = "Pagos fijos",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 40.dp)
                                .padding(end = 48.dp)
                        )
                    }
                }

            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    error != null -> {
                        Text(text = "Error: $error", color = Color.Red)
                    }
                }
            }

            items(fixedPayments) { payment ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    RowCard(
                        icon = Icons.Default.Payment,
                        iconColor = Color(0xFF8A2BE2),
                        iconBgColor = Color(0x268A2BE2),
                        title = payment.nombre,
                        subtitle = "Vence el día ${payment.diaVencimiento} • ${payment.categoria.nombre}",
                        monto = "$${payment.monto}"
                    )
                }
            }

            item {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    FixedPaymentTotalBanner(
                        title = "Total mensual en pagos fijos",
                        totalAmount = "$${total}"
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}