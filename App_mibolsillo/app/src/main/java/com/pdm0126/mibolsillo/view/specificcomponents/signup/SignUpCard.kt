package com.pdm0126.mibolsillo.view.specificcomponents.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.CircularProgressIndicator
import com.pdm0126.mibolsillo.view.components.buttons.LoadingButton

@Composable
fun SignUpCard(
    modifier: Modifier = Modifier,
    error: String?,
    loading: Boolean,
    onRegister: (
        nombre: String,
        email: String,
        password: String,
        confirmPassword: String
    ) -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var visiblePassword by remember { mutableStateOf(false) }
    var visibleConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {

            Text(
                "NOMBRE COMPLETO",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8A2BE2),
                fontSize = 12.sp
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = {
                    Text("nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD7B7F9),
                    unfocusedBorderColor = Color(0xFFD7B7F9)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "CORREO ELECTRÓNICO",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8A2BE2),
                fontSize = 12.sp
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "CONTRASEÑA",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8A2BE2),
                fontSize = 12.sp
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation =
                    if (visiblePassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { visiblePassword = !visiblePassword }
                    ) {
                        Icon(
                            imageVector =
                                if (visiblePassword)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD7B7F9),
                    unfocusedBorderColor = Color(0xFFD7B7F9)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "CONFIRMAR CONTRASEÑA",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8A2BE2),
                fontSize = 12.sp
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation =
                    if (visibleConfirm)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { visibleConfirm = !visibleConfirm }
                    ) {
                        Icon(
                            imageVector =
                                if (visibleConfirm)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD7B7F9),
                    unfocusedBorderColor = Color(0xFFD7B7F9)
                )
            )

            if (error != null) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = error,
                    color = Color.Red
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            LoadingButton(
                text = "Crear mi cuenta",
                loading = loading,
                onClick = {

                    onRegister(
                        nombre,
                        correo,
                        password,
                        confirmPassword
                    )

                }
            )
        }
    }
}