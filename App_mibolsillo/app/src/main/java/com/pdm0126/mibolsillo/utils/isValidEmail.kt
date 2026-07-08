package com.pdm0126.mibolsillo.utils

fun isValidEmail(email: String): Boolean {

    return android.util.Patterns.EMAIL_ADDRESS
        .matcher(email)
        .matches()

}