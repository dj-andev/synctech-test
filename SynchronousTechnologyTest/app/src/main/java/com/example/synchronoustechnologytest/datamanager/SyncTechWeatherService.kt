package com.example.synchronoustechnologytest.datamanager

import android.content.Context
import com.example.synchronoustechnologytest.SyncTechWeatherContract
import com.example.synchronoustechnologytest.retrofit.ServiceAbstract
import io.reactivex.Observable
import okhttp3.ResponseBody

class SyncTechWeatherService(context: Context): ServiceAbstract(context), SyncTechWeatherContract.Service {

    override fun fetchCurrentWeatherForecast(lat: Float, lon: Float, exclude: String, appId: String): Observable<ResponseBody> {
        return getObservableFromCall(retrofit.fetchCurrentWeather(lat = lat, lon = lon, exclude = exclude, appId = appId))
    }
}