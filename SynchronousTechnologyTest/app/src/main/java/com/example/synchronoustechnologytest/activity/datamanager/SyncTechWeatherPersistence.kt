package com.example.synchronoustechnologytest.activity.datamanager

import android.content.Context
import com.example.synchronoustechnologytest.activity.SyncTechWeatherContract
import com.example.synchronoustechnologytest.base.SharedPreference
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SyncTechWeatherPersistence(context: Context) : SyncTechWeatherContract.Persistence,
    KoinComponent {
    private val mSharedPreference: SharedPreference by inject { parametersOf(context) }

    override fun setLat(lat: Float) {
        mSharedPreference.putLat(lat)
    }

    override fun setLon(lon: Float) {
        mSharedPreference.putLon(lon)
    }

    override fun setResponse(response: String) {
        mSharedPreference.putResponse(response)
    }

    override fun getLat(): Float {
        return mSharedPreference.getLat()
    }

    override fun getLon(): Float {
        return mSharedPreference.getLon()
    }

    override fun getResponse(): String {
        return mSharedPreference.getResponse()
    }
}