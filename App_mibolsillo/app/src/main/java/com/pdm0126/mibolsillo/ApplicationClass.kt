package com.pdm0126.mibolsillo

import android.app.Application
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        OneSignal.initWithContext(
            this,
            "ee35f02e-7ccb-408a-8719-8a70f89eb8f8"
        )
    }
}
