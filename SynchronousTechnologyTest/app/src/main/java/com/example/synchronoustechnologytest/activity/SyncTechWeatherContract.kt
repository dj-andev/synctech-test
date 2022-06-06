package com.example.synchronoustechnologytest.activity

import androidx.lifecycle.LiveData
import com.example.synchronoustechnologytest.model.Weather
import com.example.synchronoustechnologytest.activity.state.SyncTechWeatherState
import com.example.synchronoustechnologytest.model.Forecast
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

interface SyncTechWeatherContract {

    interface ViewModel {
        fun getViewState(): LiveData<SyncTechWeatherState>
        fun updateWeatherInfo(forecast: Forecast)
        fun fetchWeatherReport(lat: Float, lon: Float)
        fun addDispose(dispose: Disposable)
        fun showError(message: String?)
    }

    interface Presenter{
        fun fetchCurrentWeatherForecast(lat: Float, lon: Float, appId: String)
        fun setLat(lat: Float)
        fun setLon(lon: Float)
        fun getLat(): Float
        fun getLon(): Float
        fun getResponse(): Forecast?
        fun getDateFromTimeStamp(timeStamp: Long): String
        fun getTimeFromTimeStamp(timeStamp: Long): String
        fun scheduleNextWeatherReport()
    }

    interface Repository{
        fun fetchCurrentWeatherForecast(lat: Float, lon: Float, appId: String): Observable<Forecast>
        fun setLat(lat: Float)
        fun setLon(lon: Float)
        fun setResponse(response: String)
        fun getLat(): Float
        fun getLon(): Float
        fun getResponse(): String
    }

    interface Service{
        fun fetchCurrentWeatherForecast(lat: Float, lon: Float, appId: String): Observable<Forecast>
    }

    interface Persistence{
        fun setLat(lat: Float)
        fun setLon(lon: Float)
        fun setResponse(response: String)
        fun getLat(): Float
        fun getLon(): Float
        fun getResponse(): String
    }
}