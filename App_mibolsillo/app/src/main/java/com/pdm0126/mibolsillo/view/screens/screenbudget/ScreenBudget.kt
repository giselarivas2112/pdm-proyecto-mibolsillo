package com.pdm0126.mibolsillo.view.screens.screenbudget

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.view.screenspecific.AlertSliderSection
import com.pdm0126.mibolsillo.view.screenspecific.MesAnio
import com.pdm0126.mibolsillo.view.screenspecific.MonthYearSection
import com.pdm0126.mibolsillo.model.Category
import com.pdm0126.mibolsillo.view.components.buttons.LoadingButton
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.forms.AmountCard
import com.pdm0126.mibolsillo.view.components.forms.CategoryDropdownLoader
import com.pdm0126.mibolsillo.view.components.forms.DescriptionSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenBudget(
    navigationBack: () -> Unit,
    viewModel: BudgetViewModel = viewModel()
) {

    var budgetAmount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var alertThreshold by remember { mutableStateOf(0.8f) }
    var budgetNotes by remember { mutableStateOf("") }
    var selectedMesAnio by remember { mutableStateOf<MesAnio?>(null) }
    val context = LocalContext.current

    val categories by viewModel.categories.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            Toast.makeText(context, "Presupuesto guardado con éxito", Toast.LENGTH_SHORT).show()
            viewModel.resetState()
            budgetAmount = ""
            selectedCategory = null
            selectedMesAnio = null
            alertThreshold = 0.8f
            budgetNotes = ""
        }
    }

    LaunchedEffect(error) {
        error?.let { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
    }

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
                        text = "Agregar presupuesto",
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
            Text(
                text = "Define cuánto puedes gastar este mes",
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            val loadingCategories by viewModel.loadingCategories.collectAsState()
            val errorCategories by viewModel.errorCategories.collectAsState()

           CategoryDropdownLoader(
                categories = categories,
                loading = loadingCategories,
                error = errorCategories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onRetry = { viewModel.loadCategories() }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            AmountCard(
                amount = budgetAmount,
                onAmountChange = { budgetAmount = it },
                title = "MONTO DEL PRESUPUESTO"
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            MonthYearSection(
                selected = selectedMesAnio,
                onSelected = { selectedMesAnio = it }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            AlertSliderSection(
                sliderValue = alertThreshold,
                onValueChange = { alertThreshold = it },
                totalBudget = budgetAmount
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            DescriptionSection(
                description = budgetNotes,
                onDescriptionChange = { nuevoTexto -> budgetNotes = nuevoTexto }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            LoadingButton(
                text = "Guardar",
                loading = loading,
                onClick = {
                    val categoria = selectedCategory
                    val monto = budgetAmount.toDoubleOrNull()
                    val mesAnio = selectedMesAnio

                    when {
                        categoria == null -> Toast.makeText(
                            context,
                            "Selecciona una categoría",
                            Toast.LENGTH_SHORT
                        ).show()

                        mesAnio == null -> Toast.makeText(
                            context,
                            "Selecciona el mes",
                            Toast.LENGTH_SHORT
                        ).show()

                        monto == null || monto <= 0 -> Toast.makeText(
                            context,
                            "Ingresa un monto válido",
                            Toast.LENGTH_SHORT
                        ).show()

                        else -> {
                            viewModel.createBudget(
                                categoriaId = categoria.id,
                                montoLimite = monto,
                                mes = mesAnio.mes,
                                anio = mesAnio.anio,
                                alertaPorcentaje = (alertThreshold * 100).toInt(),
                                notas = budgetNotes.ifBlank { null }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
        }

    }
}