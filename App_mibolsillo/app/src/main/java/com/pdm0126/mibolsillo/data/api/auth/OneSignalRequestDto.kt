package com.pdm0126.mibolsillo.data.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class OneSignalRequestDto(
    val onesignal_id: String
)
