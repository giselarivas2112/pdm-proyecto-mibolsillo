package com.pdm0126.mibolsillo.navegation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdm0126.mibolsillo.view.screens.screenregisterfixedpayment.ScreenRegisterFixedPayment
import com.pdm0126.mibolsillo.view.screens.screenregisterbudget.ScreenRegisterBudget
import com.pdm0126.mibolsillo.view.screens.screenregistercategory.ScreenRegisterCategory
import com.pdm0126.mibolsillo.view.screens.screendashboard.ScreenDashboard
import com.pdm0126.mibolsillo.view.screens.screenregisterexpense.ScreenRegisterExpense
import com.pdm0126.mibolsillo.view.screens.screenhome.ScreenHome
import com.pdm0126.mibolsillo.view.screens.screenlogin.ScreenAuthCheck
import com.pdm0126.mibolsillo.view.screens.screenlogin.ScreenLogin
import com.pdm0126.mibolsillo.view.screens.screenmyexpenses.ScreenMyExpenses
import com.pdm0126.mibolsillo.view.screens.screenprofile.ScreenProfile
import com.pdm0126.mibolsillo.view.screens.screensignup.ScreenSignUp
import com.pdm0126.mibolsillo.view.screens.screenstats.ScreenStats
import com.pdm0126.mibolsillo.view.screens.screenviewbudgets.ScreenViewBudgets
import com.pdm0126.mibolsillo.view.screens.screenviewcategory.ScreenViewCategory
import com.pdm0126.mibolsillo.view.screens.screenviewfixedpayments.ScreenViewFixedPayments

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
                ScreenSignUp(
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
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToViewReports = { backStack.add(Route.PantallaViewReports) }
                )
            }
            entry<Route.PantallaExpense> {
                ScreenRegisterExpense(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaBudget> {
                ScreenRegisterBudget(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaCategory> {
                ScreenRegisterCategory(
                    navigationBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Route.PantallaRegisterFixedPayment> {

                ScreenRegisterFixedPayment(
                    navigationBack = {
                        println("STACK ANTES: ${backStack.toList()}")

                        backStack.removeLastOrNull()

                        println("STACK DESPUÉS: ${backStack.toList()}")
                    }
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
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToViewReports = { backStack.add(Route.PantallaViewReports) }
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
                    navegationToViewbudgets = { backStack.add(Route.PantallaViewbudgets) },
                    navigationToHome = { backStack.add(Route.PantallaHome) },
                    navigationToViewReports = {backStack.add(Route.PantallaViewReports)}
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
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navigationToViewReports = {backStack.add(Route.PantallaViewReports)}
                )
            }
            entry<Route.PantallaViewFixedPayment> {
                ScreenViewFixedPayments(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navigationToViewReports = {backStack.add(Route.PantallaViewReports)}
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
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navigationToViewReports = {backStack.add(Route.PantallaViewReports)}
                )
            }
            entry<Route.PantallaViewReports> {
                ScreenStats(
                    navigationBack = { backStack.removeLastOrNull() },
                    navigationToDashboard = { backStack.add(Route.PantallaDashboard) },
                    navigationToExpenses = { backStack.add(Route.PantallaExpenses) },
                    navigationToPerfil = { backStack.add(Route.PantallaProfile) },
                    navigationToExpense = { backStack.add(Route.PantallaExpense) },
                    navegationToBudget = { backStack.add(Route.PantallaBudget) },
                    navegationToCategory = { backStack.add(Route.PantallaCategory) },
                    navegationToFixedPayment = { backStack.add(Route.PantallaRegisterFixedPayment) },
                    navigationToViewReports = {backStack.add(Route.PantallaViewReports)}
                )
            }
        }
    )
}
