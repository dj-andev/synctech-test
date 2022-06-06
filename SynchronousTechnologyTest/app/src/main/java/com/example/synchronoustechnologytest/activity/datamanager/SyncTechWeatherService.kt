package com.example.synchronoustechnologytest.activity.datamanager

import android.content.Context
import com.example.synchronoustechnologytest.activity.SyncTechWeatherContract
import com.example.synchronoustechnologytest.model.Forecast
import com.example.synchronoustechnologytest.retrofit.ServiceAbstract
import io.reactivex.Observable
import okhttp3.ResponseBody

class SyncTechWeatherService(context: Context): ServiceAbstract(context), SyncTechWeatherContract.Service {

    override fun fetchCurrentWeatherForecast(lat: Float, lon: Float, appId: String): Observable<Forecast> {
        return getObservableFromCall(retrofit.fetchCurrentWeather(lat = lat, lon = lon, appId = appId))
    }
}