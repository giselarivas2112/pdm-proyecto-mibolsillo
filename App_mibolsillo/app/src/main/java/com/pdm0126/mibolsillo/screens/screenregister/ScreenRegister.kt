package com.pdm0126.mibolsillo.screens.screenregister

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.mibolsillo.components.RegisterCard

@Composable
fun ScreenRegister(
    navigationToHome: () -> Unit,
    navigationToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {

    val user by viewModel.user.collectAsState()
    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(user) {
        if (user != null) {
            viewModel.clearUser()

            navigationToHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F3FA))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color(0xFF8A2BE2))
        )

        IconButton(
            onClick = { navigationToLogin() },
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Crear cuenta",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Es gratis, solo toma un momento",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            item {

                RegisterCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 24.dp,
                            end = 24.dp
                        ),
                    error = error,
                    loading = loading,
                    onRegister = { nombre, email, password ->

                        viewModel.register(
                            nombre = nombre,
                            email = email,
                            password = password
                        )
                    }
                )
            }

            item {

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "¿Ya tienes cuenta?",
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = navigationToLogin
                    ) {
                        Text(
                            text = "Inicia sesión",
                            color = Color(0xFF8A2BE2),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}