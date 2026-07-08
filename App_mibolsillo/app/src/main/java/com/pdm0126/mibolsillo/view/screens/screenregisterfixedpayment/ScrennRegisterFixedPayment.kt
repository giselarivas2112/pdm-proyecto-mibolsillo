package com.pdm0126.mibolsillo.view.screens.screenregisterfixedpayment

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.data.model.Category
import com.pdm0126.mibolsillo.view.components.buttons.LoadingButton
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.forms.AmountCard
import com.pdm0126.mibolsillo.view.components.forms.CategoryDropdownLoader
import com.pdm0126.mibolsillo.view.components.forms.NameSection
import com.pdm0126.mibolsillo.view.specificcomponents.registerfixedpayment.ReminderSection

@Composable
fun ScreenRegisterFixedPayment(
    navigationBack: () -> Unit,
    viewModel: RegisterFixedPaymentViewModel = viewModel()
) {
    val context = LocalContext.current

    var nombrePago by remember { mutableStateOf("") }
    var montoPago by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<Category?>(null) }
    var diaVencimiento by remember { mutableStateOf<Int?>(null) }
    var diasRecordatorio by remember { mutableStateOf<Int?>(null) }

    val categories by viewModel.categories.collectAsState()
    val loadingCategories by viewModel.loadingCategories.collectAsState()
    val errorCategories by viewModel.errorCategories.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            Toast.makeText(context, "Pago fijo registrado exitosamente", Toast.LENGTH_SHORT).show()
            viewModel.resetState()

        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.resetError()
        }
    }

    PullToRefreshBox(
        isRefreshing = refreshing,
        onRefresh = { viewModel.loadCategories(isRefresh = true) },
        modifier = Modifier
            .fillMaxSize()
    ){

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                HeaderSection()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = navigationBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Registrar pago fijo",
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
            NameSection(
                name = nombrePago,
                onNameChange = { nombrePago = it },
                title = "NOMBRE DEL PAGO",
                placeholder = "Ej: Netflix, Renta, Luz..."
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            CategoryDropdownLoader(
                categories = categories,
                loading = loadingCategories,
                error = errorCategories,
                selectedCategory = categoriaSeleccionada,
                onCategorySelected = { categoriaSeleccionada = it },
                onRetry = { viewModel.loadCategories() }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Monto
        item {
            AmountCard(
                amount = montoPago,
                onAmountChange = { montoPago = it },
                title = "MONTO DEL PAGO FIJO"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            DiaVencimientoSection(
                diaSeleccionado = diaVencimiento,
                onDiaSelected = { diaVencimiento = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            DiasRecordatorioSection(
                diasSeleccionados = diasRecordatorio,
                onDiasSelected = { diasRecordatorio = it }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        // Resumen de recordatorio (solo si ambos valores están seleccionados)
        item {
            if (diaVencimiento != null && diasRecordatorio != null) {
                ReminderSection(
                    diaAviso = (
                            if (diaVencimiento!! - diasRecordatorio!! <= 0) {
                                31 + (diaVencimiento!! - diasRecordatorio!!)
                            } else {
                                diaVencimiento!! - diasRecordatorio!!
                            }
                            ).toString(),
                    diasAntes = diasRecordatorio!!.toString()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        // Botón guardar
        item {
            LoadingButton(
                text = "Registrar pago fijo",
                loading = loading,
                onClick = {
                    when {
                        nombrePago.isBlank() -> Toast.makeText(
                            context,
                            "Escribe el nombre del pago",
                            Toast.LENGTH_SHORT
                        ).show()

                        categoriaSeleccionada == null -> Toast.makeText(
                            context,
                            "Selecciona una categoría",
                            Toast.LENGTH_SHORT
                        ).show()

                        montoPago.isBlank() || montoPago.toDoubleOrNull() == null || montoPago.toDouble() <= 0 -> Toast.makeText(
                            context,
                            "Ingresa un monto válido",
                            Toast.LENGTH_SHORT
                        ).show()

                        diaVencimiento == null -> Toast.makeText(
                            context,
                            "Selecciona el día de vencimiento",
                            Toast.LENGTH_SHORT
                        ).show()

                        diasRecordatorio == null -> Toast.makeText(
                            context,
                            "Selecciona los días de recordatorio",
                            Toast.LENGTH_SHORT
                        ).show()

                        else -> {
                            viewModel.createFixedPayment(
                                nombre = nombrePago.trim(),
                                categoriaId = categoriaSeleccionada!!.id,
                                monto = montoPago.toDouble(),
                                diaVencimiento = diaVencimiento!!,
                                diasRecordatorio = diasRecordatorio!!
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(56.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiaVencimientoSection(
    diaSeleccionado: Int?,
    onDiaSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("DÍA DE VENCIMIENTO", fontWeight = FontWeight.Bold, color = Color(0xFF8A2BE2))
        Spacer(modifier = Modifier.height(12.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = diaSeleccionado?.let { "Día $it de cada mes" } ?: "Selecciona el día",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.heightIn(max = 280.dp)
            ) {
                (1..31).forEach { dia ->
                    DropdownMenuItem(
                        text = { Text("Día $dia de cada mes") },
                        onClick = { onDiaSelected(dia); expanded = false }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiasRecordatorioSection(
    diasSeleccionados: Int?,
    onDiasSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("RECORDATORIO PREVIO", fontWeight = FontWeight.Bold, color = Color(0xFF8A2BE2))
        Spacer(modifier = Modifier.height(12.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = diasSeleccionados?.let { "$it ${if (it == 1) "día" else "días"} antes" } ?: "Selecciona cuántos días antes",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                (1..3).forEach { dias ->
                    DropdownMenuItem(
                        text = { Text("$dias ${if (dias == 1) "día" else "días"} antes") },
                        onClick = { onDiasSelected(dias); expanded = false }
                    )
                }
            }
        }
    }
}