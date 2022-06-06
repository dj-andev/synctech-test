package com.example.synchronoustechnologytest.workermanager

import android.content.Context
import android.util.Log
import com.example.synchronoustechnologytest.BuildConfig
import com.example.synchronoustechnologytest.activity.SyncTechWeatherContract
import com.example.synchronoustechnologytest.model.Forecast
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class ScheduleWeatherCast(val context: Context): KoinComponent {

    lateinit var repository: SyncTechWeatherContract.Repository
    init {
        val service = get<SyncTechWeatherContract.Service> { parametersOf(context) }
        val persistence = get<SyncTechWeatherContract.Persistence> { parametersOf(context) }
        repository =
            get<SyncTechWeatherContract.Repository> { parametersOf(service, persistence) }
    }

    fun fetchWeatherReport() {
        repository.fetchCurrentWeatherForecast(lat = repository.getLat(),
            lon = repository.getLon(),
            appId = BuildConfig.api_key)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(object: Observer<Forecast>{
                override fun onSubscribe(disposable: Disposable) {

                }

                override fun onNext(forecast: Forecast) {
                    val forecastString = Gson().toJson(forecast)
                    Log.i("SYNC_TEST", "${forecastString}")
                    repository.apply {
                        setResponse(forecastString)
                        setLat(repository.getLat())
                        setLon(repository.getLon())
                    }
                    Log.i("SYNC_TEST", "$forecastString")
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

}