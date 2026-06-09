package com.pdm0126.mibolsillo.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.mibolsillo.screens.screenlogin.ScreenLogin
import com.pdm0126.mibolsillo.screens.screenregister.ScreenRegister

@Composable
fun MainNavegation() {

    val backStack = rememberNavBackStack(Route.PantallaLogin)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },

        entryProvider = entryProvider {

            entry<Route.PantallaLogin> {

                ScreenLogin(navigationToHome = {
                    // navegar al Home
                },
                    navegationToRegister = { backStack.add(Route.PantallaRegister) }
                )
            }

            entry<Route.PantallaRegister> {
                ScreenRegister(navigationToHome = {
                    // navegar al Home
                },
                    navigationToLogin = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
