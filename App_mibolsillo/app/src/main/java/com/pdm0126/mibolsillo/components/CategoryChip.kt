package com.pdm0126.mibolsillo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Card(modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                if (selected)
                    Color(0xFF8A2BE2)
                else
                    Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFFE0D4F7))
    ) {

        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            color =
                if (selected)
                    Color.White
                else
                    Color(0xFF8A2BE2)
        )
    }
}