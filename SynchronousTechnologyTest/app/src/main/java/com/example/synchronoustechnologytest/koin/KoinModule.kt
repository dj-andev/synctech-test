package com.example.synchronoustechnologytest.koin

import android.app.Application
import com.example.synchronoustechnologytest.activity.di.weatherModule
import com.example.synchronoustechnologytest.base.di.sharedPreferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Used to start the KOIN, also load the DI fod required modules
 */
object KoinModule {

    fun loadModules(context: Application) {
        startKoin {
            androidContext(context)
            modules(
                listOf(
                    weatherModule,
                    sharedPreferencesModule
                )
            )
        }
    }
}