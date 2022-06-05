package com.example.synchronoustechnologytest

import android.app.Application
import com.example.synchronoustechnologytest.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Used to start the KOIN, also load the DI fod required modules
 */
object KoinModule {

    fun loadModules(context: Application){
        startKoin {
            androidContext(context)
            modules(listOf(weatherModule))
        }
    }
}