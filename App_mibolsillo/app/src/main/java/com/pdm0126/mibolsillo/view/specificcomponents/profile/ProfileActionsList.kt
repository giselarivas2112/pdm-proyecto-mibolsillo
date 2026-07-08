package com.pdm0126.mibolsillo.view.specificcomponents.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdm0126.mibolsillo.view.components.profile.ProfileActionButton

@Composable
fun ProfileActionsList(
    onVerPresupuestos: () -> Unit,
    onVerCategorias: () -> Unit,
    onVerPagosFijos: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileActionButton(
            text = "Ver presupuestos creados",
            icon = Icons.Default.AttachMoney,
            iconColor = Color(0xFFFFA000),
            iconBgColor = Color(0x26FFA000),
            onClick = onVerPresupuestos
        )

        ProfileActionButton(
            text = "Ver categorías creadas",
            icon = Icons.Default.Category,
            iconColor = Color(0xFFAB47BC),
            iconBgColor = Color(0x26AB47BC),
            onClick = onVerCategorias
        )

        ProfileActionButton(
            text = "Ver pagos fijos",
            icon = Icons.Default.CalendarMonth,
            iconColor = Color(0xFF42A5F5),
            iconBgColor = Color(0x2642A5F5),
            onClick = onVerPagosFijos
        )

      ProfileActionButton(
            text = "Cerrar sesión",
            icon = Icons.AutoMirrored.Filled.Logout,
            iconColor = Color(0xFFE57373),
            iconBgColor = Color(0x26E57373),
            textColor = Color(0xFFD32F2F),
            onClick = onCerrarSesion
        )
    }
}