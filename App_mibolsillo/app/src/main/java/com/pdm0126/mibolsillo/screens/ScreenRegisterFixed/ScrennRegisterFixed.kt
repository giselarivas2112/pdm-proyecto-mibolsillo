package com.pdm0126.mibolsillo.screens.ScreenRegisterFixed

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pdm0126.mibolsillo.components.CategorySection
import com.pdm0126.mibolsillo.components.DateSection
import com.pdm0126.mibolsillo.components.ExpenseAmountCard
import com.pdm0126.mibolsillo.components.HeaderSection
import com.pdm0126.mibolsillo.components.NameSection
import com.pdm0126.mibolsillo.components.RecordatorioPrevioSection


@Composable
fun ScreenRegisterFixedPayment(navigationBack: () -> Unit) {

    var nombrePago by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("") }
    var montoPago by remember { mutableStateOf("") }
    var diaVencimiento by remember { mutableStateOf("Día 20 de cada mes") }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
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
                onNameChange = { nombrePago = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            CategorySection(
                selectedCategory = categoriaSeleccionada,
                onCategorySelected = { categoriaSeleccionada = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            ExpenseAmountCard(
                amount = montoPago,
                onAmountChange = { montoPago = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            DateSection(
                selectedDate = diaVencimiento,
                onDateSelected = { diaVencimiento = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            RecordatorioPrevioSection(
                diaAviso = "17",
                diasAntes = "3"
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Button(
                onClick = {
                    Toast.makeText(context, "Pago fijo guardado con éxito", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2))
            ) {
                Text(text = "Registrar pago fijo", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}