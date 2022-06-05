package com.example.synchronoustechnologytest.base

import android.view.View

interface BaseActivityContract {
    fun getLayout(): Int
    fun initiateViews()
    fun attachViewModelsWithViews()
    fun attachObserver()
    fun getPageTitle(): Int
    fun showSnackBar(view: View, message: String)
}