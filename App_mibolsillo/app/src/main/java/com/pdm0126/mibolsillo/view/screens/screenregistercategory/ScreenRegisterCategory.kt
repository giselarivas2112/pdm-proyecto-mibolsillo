package com.pdm0126.mibolsillo.view.screens.screenregistercategory

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.pdm0126.mibolsillo.view.components.common.RowCard
import com.pdm0126.mibolsillo.view.components.buttons.LoadingButton
import com.pdm0126.mibolsillo.view.components.common.HeaderSection
import com.pdm0126.mibolsillo.view.components.forms.NameSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenRegisterCategory(
    navigationBack: () -> Unit,
    viewModel: RegisterCategoryViewModel = viewModel()
) {
    var categoryName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("🍔") }
    val context = LocalContext.current
    val iconList = listOf("🍔", "🚗", "🏠", "⚡", "🎬", "🛒", "💊", "📚", "✈️", "🐾")

    val error by viewModel.error.collectAsState()
    val success by viewModel.success.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            Toast.makeText(context, "Categoría '$categoryName' creada con éxito", Toast.LENGTH_SHORT).show()
            viewModel.resetState()
            categoryName = ""
            selectedIcon = "🍔"
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetError()
        }
    }

    PullToRefreshBox(
        isRefreshing = refreshing,
        onRefresh = { viewModel.getCategories(isRefresh = true) },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    HeaderSection()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = navigationBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Nueva categoría",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 40.dp)
                                .padding(end = 48.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color(0xFFEFE5FD), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color(0xFF8A2BE2), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = selectedIcon, fontSize = 32.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Vista previa de la categoría",
                    color = Color(0xFF8A2BE2),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                NameSection(
                    name = categoryName,
                    onNameChange = { categoryName = it },
                    title = "NOMBRE DE LA CATEGORÍA",
                    placeholder = "Ingresar nombre categoría"
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "ÍCONO",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8A2BE2),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFFD7B7F9)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val rows = iconList.chunked(5)
                            rows.forEach { rowIcons ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    rowIcons.forEach { icon ->
                                        val isSelected = selectedIcon == icon
                                        Box(
                                            modifier = Modifier
                                                .padding(vertical = 8.dp)
                                                .size(45.dp)
                                                .background(
                                                    color = if (isSelected) Color(0xFF8A2BE2) else Color(0xFFF3E8FF),
                                                    shape = CircleShape
                                                )
                                                .clickable { selectedIcon = icon },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = icon, fontSize = 20.sp)
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                        }
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            item {
                LoadingButton(
                    text = "Crear categoría",
                    loading = loading,
                    onClick = { viewModel.createCategory(categoryName, selectedIcon) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(56.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}