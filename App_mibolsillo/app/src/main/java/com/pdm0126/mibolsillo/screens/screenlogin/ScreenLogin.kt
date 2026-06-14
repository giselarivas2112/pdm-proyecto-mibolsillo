package com.pdm0126.mibolsillo.screens.screenlogin

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pdm0126.mibolsillo.components.LoginCard

@Composable
fun ScreenLogin(navigationToHome: () -> Unit, navegationToRegister: () -> Unit,
                navegationToDashboard: () -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F3FA))
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(Color(0xFF8A2BE2))
        )

        IconButton(onClick = { navigationToHome() },
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 40.dp
                )
                .zIndex(10f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {

                Spacer(modifier = Modifier.height(60.dp))

                Box(modifier = Modifier
                    .size(90.dp)
                    .background(
                        Color.White.copy(alpha = 0.15f),
                        CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {

                    Box(modifier = Modifier
                        .size(60.dp)
                        .background(
                            Color.White.copy(alpha = 0.25f),
                            CircleShape
                        ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "FF",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Iniciar sesión",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {

                LoginCard(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                    navigationToHome = navigationToHome,
                    navegationToDashboard = navegationToDashboard
                )
            }

            item {

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "¿No tienes cuenta?",
                    color = Color.Gray
                )

                TextButton(onClick = navegationToRegister) {
                    Text(
                        text = "Regístrate gratis",
                        color = Color(0xFF8A2BE2),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}