package com.pdm0126.mibolsillo.components

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSection(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
    val selectedCalendar = Calendar.getInstance()

            selectedCalendar.set(
                year,
                month,
                dayOfMonth
            )

            val formatter = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            )

            onDateSelected(
                formatter.format(
                    selectedCalendar.time
                )
            )

        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        Text(
            text = "FECHA",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A2BE2)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    datePickerDialog.show()
                },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                Color(0xFFD7B7F9)
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 18.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF8A2BE2)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = selectedDate,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color(0xFF8A2BE2)
                )
            }
        }
    }
}