package com.example.synchronoustechnologytest.base.di

import android.content.Context
import com.example.synchronoustechnologytest.base.SharedPreference
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single { (context: Context) -> SharedPreference.getInstance(context) }
}