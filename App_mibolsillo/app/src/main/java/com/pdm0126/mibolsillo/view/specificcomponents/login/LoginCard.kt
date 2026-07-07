package com.pdm0126.mibolsillo.view.specificcomponents.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginCard(
    modifier: Modifier = Modifier,
    error: String?,
    loading: Boolean,
    onLogin: (String, String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    Card(modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 24.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "CORREO ELECTRÓNICO",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A2BE2),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text("correo")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFD7B7F9),
                        unfocusedBorderColor = Color(0xFFD7B7F9)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "CONTRASEÑA",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A2BE2),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    visualTransformation =
                        if (visible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                visible = !visible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (visible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Color(0xFF8A2BE2)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFD7B7F9),
                        unfocusedBorderColor = Color(0xFFD7B7F9)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color(0xFF8A2BE2),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onLogin(
                        email,
                        password
                    )
                },
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA020F0)
                )
            ) {
                if (loading) {

                    CircularProgressIndicator(
                        strokeWidth = 2.dp
                    )

                } else {

                Text(
                    text = "Entrar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            }
            if (error != null) {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = error,
                    color = Color.Red
                )
            }

        }
    }
}