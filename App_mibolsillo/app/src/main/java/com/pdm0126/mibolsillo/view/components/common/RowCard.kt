package com.pdm0126.mibolsillo.view.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowCard(
    emojiIcon: String? = null,
    icon: ImageVector? = null,
    iconColor: Color = Color(0xFF4A148C),
    iconBgColor: Color,
    title: String,
    subtitle: String? = null,
    monto: String? = null,
    montoColor: Color = Color(0xFF4A148C),
    showCard: Boolean = true,
    onCardClick: () -> Unit = {}
) {
    if (showCard) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClick() },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            RowCardContent(
                emojiIcon, icon, iconColor, iconBgColor,
                title, subtitle, monto, montoColor,
                rowModifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            )
        }
    } else {
        RowCardContent(
            emojiIcon, icon, iconColor, iconBgColor,
            title, subtitle, monto, montoColor,
            rowModifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun RowCardContent(
    emojiIcon: String?,
    icon: ImageVector?,
    iconColor: Color,
    iconBgColor: Color,
    title: String,
    subtitle: String?,
    monto: String?,
    montoColor: Color,
    rowModifier: Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(rowModifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            if (emojiIcon != null) {
                Text(text = emojiIcon, fontSize = 22.sp)
            } else if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color(0xFF4A148C),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            if (!subtitle.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        if (monto != null) {
            Text(
                text = monto,
                color = montoColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}