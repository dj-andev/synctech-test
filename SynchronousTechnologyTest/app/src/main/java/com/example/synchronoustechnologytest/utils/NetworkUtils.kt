package com.example.synchronoustechnologytest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {
    fun isWIFIConnected(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities?.let { it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)} ?: kotlin.run { return false }
        }else{
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.type == ConnectivityManager.TYPE_WIFI && networkInfo.isAvailable && (networkInfo.isConnected || networkInfo.isConnectedOrConnecting)
        }
    }

    object RESPONSE_CODE {
        const val SUCCESS = 200
        const val BAD_REQUEST = 400
        const val UNAUTHORISED = 401
        const val FORBIDDEN = 403
        const val CLIENT_CLOSED_REQUEST = 499
        const val SERVER_BUSY = 504

    }
}