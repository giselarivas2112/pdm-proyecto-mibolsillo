package com.pdm0126.mibolsillo.navegation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Route : NavKey {

    @Serializable
    data object PantallaLogin : Route()

    @Serializable
    data object  PantallaRegister : Route()
        @Serializable
        data object PantallaHome : Route()
}
