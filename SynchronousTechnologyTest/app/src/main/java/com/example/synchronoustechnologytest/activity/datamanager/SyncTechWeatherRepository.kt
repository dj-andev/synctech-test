package com.example.synchronoustechnologytest.activity.datamanager

import com.example.synchronoustechnologytest.activity.SyncTechWeatherContract
import com.example.synchronoustechnologytest.model.Forecast
import io.reactivex.Observable
import okhttp3.ResponseBody

class SyncTechWeatherRepository(val persistence: SyncTechWeatherContract.Persistence,
                                val service: SyncTechWeatherContract.Service): SyncTechWeatherContract.Repository  {

    override fun fetchCurrentWeatherForecast(lat: Float, lon: Float, appId: String): Observable<Forecast> {
        return service.fetchCurrentWeatherForecast(lat = lat, lon = lon, appId = appId)
    }

    override fun setLat(lat: Float) {
        persistence.setLat(lat)
    }

    override fun setLon(lon: Float) {
       persistence.setLon(lon)
    }

    override fun setResponse(response: String) {
        persistence.setResponse(response)
    }

    override fun getLat(): Float {
       return persistence.getLat()
    }

    override fun getLon(): Float {
        return persistence.getLon()
    }

    override fun getResponse(): String {
        return persistence.getResponse()
    }
}