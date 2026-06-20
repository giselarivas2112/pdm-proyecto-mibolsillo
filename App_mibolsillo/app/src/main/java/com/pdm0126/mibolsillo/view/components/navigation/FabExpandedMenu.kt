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

            _root_ide_package_.com.pdm0126.mibolsillo.view.components.buttons.MenuButton(
                "Agregar categoría",
                Icons.Default.Category,
                onCategory
            )

            _root_ide_package_.com.pdm0126.mibolsillo.view.components.buttons.MenuButton(
                "Agregar presupuesto",
                Icons.Default.AttachMoney,
                onBudget
            )

            _root_ide_package_.com.pdm0126.mibolsillo.view.components.buttons.MenuButton(
                "Agregar pago fijo",
                Icons.Default.DateRange,
                onFixedPayment
            )

            _root_ide_package_.com.pdm0126.mibolsillo.view.components.buttons.MenuButton(
                "Agregar gasto",
                Icons.Default.ShoppingCart,
                onExpense
            )
        }
    }
}