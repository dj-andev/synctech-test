package com.example.synchronoustechnologytest

import android.app.Application
import org.koin.core.component.KoinComponent

/**
 * Application class for the app
 */
class SyncTechApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        //starting the koin
        KoinModule.loadModules(this@SyncTechApplication)
    }
}