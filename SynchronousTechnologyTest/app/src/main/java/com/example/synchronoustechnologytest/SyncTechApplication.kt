package com.example.synchronoustechnologytest

import android.app.Application

class SyncTechApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //starting the koin
        KoinModule.startKoin(this@SyncTechApplication)
    }
}