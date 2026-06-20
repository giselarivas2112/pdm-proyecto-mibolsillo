package com.pdm0126.mibolsillo.view.screenspecific

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import java.util.Calendar

data class MesAnio(val mes: Int, val anio: Int) {
    fun nombre(): String = "${NOMBRES_MES[mes - 1]} $anio"
}

private val NOMBRES_MES = listOf(
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearSection(
    selected: MesAnio?,
    onSelected: (MesAnio) -> Unit
) {
    val anioActual = remember { Calendar.getInstance().get(Calendar.YEAR) }
    val mesActual = remember { Calendar.getInstance().get(Calendar.MONTH) + 1 }

    val mesElegido = selected?.mes ?: mesActual
    val anioElegido = selected?.anio ?: anioActual

    var mesExpanded by remember { mutableStateOf(false) }
    var anioExpanded by remember { mutableStateOf(false) }

    val anios = remember { (anioActual - 2..anioActual + 5).toList() }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "MES Y AÑO DEL PRESUPUESTO", fontWeight = FontWeight.Bold, color = Color(0xFF8A2BE2))
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            ExposedDropdownMenuBox(
                expanded = mesExpanded,
                onExpandedChange = { mesExpanded = !mesExpanded },
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFD7B7F9))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = NOMBRES_MES[mesElegido - 1], modifier = Modifier.weight(1f))
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = mesExpanded)
                    }
                }

                ExposedDropdownMenu(
                    expanded = mesExpanded,
                    onDismissRequest = { mesExpanded = false },
                    modifier = Modifier.heightIn(max = 280.dp)
                ) {
                    NOMBRES_MES.forEachIndexed { index, nombre ->
                        DropdownMenuItem(
                            text = { Text(text = nombre) },
                            onClick = {
                                onSelected(MesAnio(index + 1, anioElegido))
                                mesExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            ExposedDropdownMenuBox(
                expanded = anioExpanded,
                onExpandedChange = { anioExpanded = !anioExpanded },
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFD7B7F9))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = anioElegido.toString(), modifier = Modifier.weight(1f))
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = anioExpanded)
                    }
                }

                ExposedDropdownMenu(
                    expanded = anioExpanded,
                    onDismissRequest = { anioExpanded = false },
                    modifier = Modifier.heightIn(max = 280.dp)
                ) {
                    anios.forEach { anio ->
                        DropdownMenuItem(
                            text = { Text(text = anio.toString()) },
                            onClick = {
                                onSelected(MesAnio(mesElegido, anio))
                                anioExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
    }
}