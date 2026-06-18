package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeBottomBar(
    pantallaActual: String,
    onInicioClick: () -> Unit,
    onMisGastosClick: () -> Unit,
    onReportesClick: () -> Unit,
    onPerfilClick: () -> Unit,
    expanded: Boolean,
    onFabClick: () -> Unit
) {
    Box {
        NavigationBar {
            NavigationBarItem(
                selected = pantallaActual == "inicio",
                onClick = onInicioClick,
                icon = {
                    Icon(Icons.Default.Home, null)
                },
                label = {
                    Text("Inicio")
                }
            )

            NavigationBarItem(
                selected = pantallaActual == "mis_gastos",
                onClick = onMisGastosClick,
                icon = {
                    Icon(Icons.Default.List, null)
                },
                label = {
                    Text("Mis gastos")
                }
            )

            NavigationBarItem(
                selected = pantallaActual == "reportes",
                onClick = onReportesClick,
                icon = {
                    Icon(Icons.Default.BarChart, null)
                },
                label = {
                    Text("Reportes")
                }
            )

            NavigationBarItem(
                selected = pantallaActual == "perfil",
                onClick = onPerfilClick,
                icon = {
                    Icon(Icons.Default.Person, null)
                },
                label = {
                    Text("Perfil")
                }
            )
        }

        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-25).dp)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}