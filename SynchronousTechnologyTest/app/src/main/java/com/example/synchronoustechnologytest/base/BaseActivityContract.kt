package com.example.synchronoustechnologytest.base

interface BaseActivityContract {
    fun getLayout(): Int
    fun initiateViews()
    fun attachObserver()
    fun getTitle(): Int
}