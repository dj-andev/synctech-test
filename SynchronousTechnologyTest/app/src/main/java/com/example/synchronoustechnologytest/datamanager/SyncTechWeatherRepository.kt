package com.example.synchronoustechnologytest.datamanager

import com.example.synchronoustechnologytest.SyncTechWeatherContract
import io.reactivex.Observable
import okhttp3.ResponseBody

class SyncTechWeatherRepository(val persistence: SyncTechWeatherContract.Persistence,
                                val service: SyncTechWeatherContract.Service): SyncTechWeatherContract.Repository  {

    override fun fetchCurrentWeatherForecast(lat: Float, lon: Float, exclude: String, appId: String): Observable<ResponseBody> {
        return service.fetchCurrentWeatherForecast(lat = lat, lon = lon, exclude = exclude, appId = appId)
    }
}