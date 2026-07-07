package com.pdm0126.mibolsillo.view.components.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdm0126.mibolsillo.view.components.buttons.MenuButton

@Composable
fun FabExpandedMenu(
    visible: Boolean,
    onCategory: () -> Unit,
    onBudget: () -> Unit,
    onFixedPayment: () -> Unit,
    onExpense: () -> Unit
) {

    AnimatedVisibility(
        visible = visible
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            MenuButton(
                "Agregar categoría",
                Icons.Default.Category,
                onCategory
            )

            MenuButton(
                "Agregar presupuesto",
                Icons.Default.AttachMoney,
                onBudget
            )

            MenuButton(
                "Agregar pago fijo",
                Icons.Default.DateRange,
                onFixedPayment
            )

            MenuButton(
                "Agregar gasto",
                Icons.Default.ShoppingCart,
                onExpense
            )
        }
    }
}