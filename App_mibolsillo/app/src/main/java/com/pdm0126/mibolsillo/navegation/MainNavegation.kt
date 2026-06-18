package com.pdm0126.mibolsillo.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.mibolsillo.screens.screenbudget.ScreenBudget
import com.pdm0126.mibolsillo.screens.screencategory.ScreenCategory
import com.pdm0126.mibolsillo.screens.screendashboard.ScreenDashboard
import com.pdm0126.mibolsillo.screens.screenexpense.ScreenExpense
import com.pdm0126.mibolsillo.screens.screenhome.ScreenHome
import com.pdm0126.mibolsillo.screens.screenlogin.ScreenAuthCheck
import com.pdm0126.mibolsillo.screens.screenlogin.ScreenLogin
import com.pdm0126.mibolsillo.screens.screenregister.ScreenRegister

@Composable
fun MainNavegation() {

    val backStack = rememberNavBackStack(Route.PantallaAuthCheck)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },

        entryProvider = entryProvider {

            entry<Route.PantallaAuthCheck> {
                ScreenAuthCheck(
                    navigateToHome = {
                        backStack.clear()
                        backStack.add(Route.PantallaHome)
                    },
                    navigateToDashboard = {
                        backStack.clear()
                        backStack.add(Route.PantallaDashboard)
                    }
                )
            }

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
                        backStack.add(Route.PantallaLogin) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) }
                )
            }
            entry<Route.PantallaExpense> {
                ScreenExpense(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaBudget> {
                ScreenBudget(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaCategory> {
                ScreenCategory(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}