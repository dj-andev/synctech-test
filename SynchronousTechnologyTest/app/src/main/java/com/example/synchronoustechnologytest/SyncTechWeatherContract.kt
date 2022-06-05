package com.example.synchronoustechnologytest

import androidx.lifecycle.LiveData
import com.example.synchronoustechnologytest.model.WeatherModel
import com.example.synchronoustechnologytest.state.SyncTechWeatherState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.http.Query

interface SyncTechWeatherContract {

    interface ViewModel {
        fun getViewState(): LiveData<SyncTechWeatherState>
        fun updateWeatherInfo(weather: WeatherModel)
        fun scheduleNextWeatherReport()
        fun fetchWeatherReport(lat: Float, lon: Float)
        fun addDispose(dispose: Disposable)
        fun showError(message: String?)
    }

    interface Presenter{
        fun fetchCurrentWeatherForecast(lat: Float, lon: Float, exclude: String, appId: String)
    }

    interface Repository{
        fun fetchCurrentWeatherForecast(lat: Float, lon: Float, exclude: String, appId: String): Observable<ResponseBody>
    }

    interface Service{
        fun fetchCurrentWeatherForecast(lat: Float, lon: Float, exclude: String, appId: String): Observable<ResponseBody>
    }

    interface Persistence{

    }
}