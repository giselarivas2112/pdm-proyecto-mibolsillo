package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderSection() {
    Box( modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color(0xFF8A2BE2))
    )
}