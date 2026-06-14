package com.pdm0126.mibolsillo.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.mibolsillo.screens.screendashboard.ScreenDashboard
import com.pdm0126.mibolsillo.screens.screenhome.ScreenHome
import com.pdm0126.mibolsillo.screens.screenlogin.ScreenLogin
import com.pdm0126.mibolsillo.screens.screenregister.ScreenRegister

@Composable
fun MainNavegation() {

    val backStack = rememberNavBackStack(Route.PantallaHome)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },

        entryProvider = entryProvider {

            entry<Route.PantallaLogin> {

                ScreenLogin(navigationToHome = { backStack.clear()
                    backStack.add(Route.PantallaHome) },
                    navegationToRegister = { backStack.add(Route.PantallaRegister) },
                   navegationToDashboard = { backStack.add(Route.PantallaDashboard)
                    }
                )
            }

            entry<Route.PantallaRegister> {
                ScreenRegister(navigationToHome = { backStack.clear()
                    backStack.add(Route.PantallaHome)
                },
                    navigationToLogin = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaHome> {
                ScreenHome(
                    navigationToLogin = { backStack.add(Route.PantallaLogin) },
                    navigationToRegister = { backStack.add(Route.PantallaRegister) }
                )
            }
            entry<Route.PantallaDashboard> {
                ScreenDashboard(
                    navigationToLogin = { backStack.clear()
                        backStack.add(Route.PantallaLogin) }
                )
            }
        }
    )
}
