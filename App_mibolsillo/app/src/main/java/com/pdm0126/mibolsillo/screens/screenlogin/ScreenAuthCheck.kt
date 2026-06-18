package com.pdm0126.mibolsillo.screens.screenlogin

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.pdm0126.mibolsillo.data.session.SessionManager
import kotlinx.coroutines.launch

@Composable
fun ScreenAuthCheck(
    navigateToHome: () -> Unit,
    navigateToDashboard: () -> Unit
) {

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {

            val token = sessionManager.getToken()

            if (token != null) {
                navigateToDashboard()
            } else {
                navigateToHome()
            }
        }
    }
}