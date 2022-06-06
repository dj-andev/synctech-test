package com.example.synchronoustechnologytest.base

import android.content.Context
import android.content.SharedPreferences

class SharedPreference private constructor(val context: Context) {
    companion object {
        private const val PREFS = "SHARED_PREF"
        private var mSharedPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null

        @Volatile
        private var instance: SharedPreference? = null

        fun getInstance(context: Context): SharedPreference =
            instance ?: synchronized(this) {
                instance ?: SharedPreference(context)
            }
    }

    annotation class Keys {
        companion object {
            const val LATITUDE = "LATITUDE"
            const val LONGITUDE = "LONGITUDE"
            const val RESPONSE = "RESPONSE"
        }
    }

    init {
        if (context != null) {
            mSharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            mEditor = mSharedPreferences?.edit()
            mEditor?.apply()
        }
    }

    private fun getInteger(@Keys key: String, defaultVal: Int): Int? {
        return mSharedPreferences?.getInt(key, defaultVal)
    }

    private fun putInteger(@Keys key: String, defaultVal: Int) {
        mEditor?.putInt(key, defaultVal)?.apply()
    }

    private fun getBoolean(@Keys key: String, defaultVal: Boolean): Boolean {
        return mSharedPreferences?.getBoolean(key, defaultVal) ?: false
    }

    private fun putBoolean(@Keys key: String, defaultVal: Boolean) {
        mEditor?.putBoolean(key, defaultVal)?.apply()
    }

    private fun getString(@Keys key: String, defaultVal: String?): String? {
        return mSharedPreferences?.getString(key, defaultVal)
    }

    private fun putString(@Keys key: String, value: String?) {
        mEditor?.putString(key, value)?.apply()
    }

    private fun getLong(@Keys key: String, defaultVal: Long): Long? {
        return mSharedPreferences?.getLong(key, defaultVal)
    }

    private fun putLong(@Keys key: String, value: Long) {
        mEditor?.putLong(key, value)?.apply()
    }

    private fun getFloat(@Keys key: String, defaultVal: Float): Float? {
        return mSharedPreferences?.getFloat(key, defaultVal)
    }

    private fun putFloat(@Keys key: String, value: Float) {
        mEditor?.putFloat(key, value)?.apply()
    }

    private fun containsKey(key: String): Boolean {
        return mSharedPreferences?.contains(key) ?: false
    }

    fun putLat(lat: Float) {
        putFloat(Keys.LATITUDE, lat)
    }

    fun getLat(): Float {
        return getFloat(Keys.LATITUDE, 0.0f) ?: 0.0f
    }

    fun putLon(lon: Float) {
        putFloat(Keys.LONGITUDE, lon)
    }

    fun getLon(): Float {
        return getFloat(Keys.LONGITUDE, 0.0f) ?: 0.0f
    }

    fun putResponse(response: String) {
        putString(Keys.RESPONSE, response)
    }

    fun getResponse(): String {
        return getString(Keys.RESPONSE, "") ?: ""
    }
}