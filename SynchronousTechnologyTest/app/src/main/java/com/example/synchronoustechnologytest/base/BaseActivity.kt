package com.example.synchronoustechnologytest.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity: AppCompatActivity(), BaseActivityContract {
    
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}