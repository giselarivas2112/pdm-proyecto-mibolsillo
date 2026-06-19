package com.pdm0126.mibolsillo.screens.screenexpense

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.pdm0126.mibolsillo.components.CategorySection
import com.pdm0126.mibolsillo.components.DateSection
import com.pdm0126.mibolsillo.components.DescriptionSection
import com.pdm0126.mibolsillo.components.ExpenseAmountCard
import com.pdm0126.mibolsillo.components.HeaderSection
import com.pdm0126.mibolsillo.components.SaveExpenseButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScreenExpense(navigationBack: () -> Unit) {

    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Comida") }
    var selectedDate by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()))
    }

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                            text = "Agregar gasto",
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

                ExpenseAmountCard(
                    amount = amount,
                    onAmountChange = {
                        amount = it
                    }
                )
        }

        item {
            /*
            Spacer(modifier = Modifier.height(20.dp))
            CategorySection(
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                }
            )

             */
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            DateSection(
                selectedDate = selectedDate,
                onDateSelected = {
                    selectedDate = it
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            DescriptionSection(
                description = description,
                onDescriptionChange = {
                    description = it
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
            SaveExpenseButton(onSave = {
                Toast.makeText(context, "¡Gasto guardado exitosamente!", Toast.LENGTH_SHORT).show()})
            }
    }
}
