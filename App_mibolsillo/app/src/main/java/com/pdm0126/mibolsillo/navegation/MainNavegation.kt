package com.pdm0126.mibolsillo.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.mibolsillo.view.screens.ScreenRegisterFixed.ScreenRegisterFixedPayment
import com.pdm0126.mibolsillo.view.screens.screenbudget.ScreenBudget
import com.pdm0126.mibolsillo.view.screens.screencategory.ScreenCategory
import com.pdm0126.mibolsillo.view.screens.screendashboard.ScreenDashboard
import com.pdm0126.mibolsillo.view.screens.screenexpense.ScreenExpense
import com.pdm0126.mibolsillo.view.screens.screenhome.ScreenHome
import com.pdm0126.mibolsillo.view.screens.screenlogin.ScreenAuthCheck
import com.pdm0126.mibolsillo.view.screens.screenlogin.ScreenLogin
import com.pdm0126.mibolsillo.view.screens.screenmyexpenses.ScreenMyExpenses
import com.pdm0126.mibolsillo.view.screens.screenperfil.ScreenProfile
import com.pdm0126.mibolsillo.view.screens.screenregister.ScreenRegister
import com.pdm0126.mibolsillo.view.screens.viewscreenbudgets.ScreenViewBudgets
import com.pdm0126.mibolsillo.view.screens.viewscreencategory.ScreenViewCategory
import com.pdm0126.mibolsillo.view.screens.viewscreenfixedpayments.ViewScreenFixedPayments

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
                    navegationToDashboard = {
                        backStack.clear()
                        backStack.add(Route.PantallaAuthCheck)
                    }
                )
            }

            entry<Route.PantallaRegister> {
                ScreenRegister(
                    navigationToHome = {
                        backStack.clear()
                        backStack.add(Route.PantallaAuthCheck)
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
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) }
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
            entry<Route.PantallaRegisterFixedPayment> {

                ScreenRegisterFixedPayment(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaExpenses> {
                ScreenMyExpenses(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) }
                )
            }
            entry<Route.PantallaProfile> {
                ScreenProfile(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navegationToViewCategory = { backStack.add(Route.PantallaViewCategory) },
                    navegationToViewFixedPayment = { backStack.add(Route.PantallaViewFixedPayment) },
                    navegationToViewbudgets = { backStack.add(Route.PantallaViewbudgets) }
                )
            }

            entry<Route.PantallaViewCategory> {
                ScreenViewCategory(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) }
                )
            }
            entry<Route.PantallaViewFixedPayment> {
                ViewScreenFixedPayments(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) }
                )
            }
            entry<Route.PantallaViewbudgets> {
                ScreenViewBudgets(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) }
                )
            }

        }
    )
}