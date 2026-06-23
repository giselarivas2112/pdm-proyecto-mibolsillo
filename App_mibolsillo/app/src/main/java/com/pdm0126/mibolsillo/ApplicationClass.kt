package com.pdm0126.mibolsillo

// FILE: ApplicationClass.kt
// PURPOSE: Initialize OneSignal when your app launches
// REQUIREMENT: Must be registered in AndroidManifest.xml

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        // Enable verbose logging to debug issues (remove in production)
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // Replace with your 36-character App ID from Dashboard > Settings > Keys & IDs
        OneSignal.initWithContext(this, "ee35f02e-7ccb-408a-8719-8a70f89eb8f8")
        CoroutineScope(Dispatchers.IO).launch {

            OneSignal.Notifications.requestPermission(false)

            kotlinx.coroutines.delay(5000)

            println(
                "ONESIGNAL_ID = ${
                    OneSignal.User.pushSubscription.id
                }"
            )
        }

        // Prompt user for push notification permission
        // In production, consider using an in-app message instead for better opt-in rates
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(false)
        }
    }
}
