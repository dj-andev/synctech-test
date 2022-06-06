package com.example.synchronoustechnologytest.activity

import android.util.Log
import com.example.synchronoustechnologytest.model.Forecast
import com.example.synchronoustechnologytest.model.Weather
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class SyncTechWeatherPresenter(
    val viewModel: SyncTechWeatherContract.ViewModel,
    val repository: SyncTechWeatherContract.Repository
) :
    SyncTechWeatherContract.Presenter {

    override fun fetchCurrentWeatherForecast(
        lat: Float,
        lon: Float,
        appId: String
    ) {
        repository.fetchCurrentWeatherForecast(
            lat = lat,
            lon = lon,
            appId = appId
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<Forecast>{
                override fun onSubscribe(d: Disposable) {
                    viewModel.addDispose(d)
                }

                override fun onNext(forecast: Forecast) {
                    val forecastString = Gson().toJson(forecast)
                    Log.i("SYNC_TEST", "${forecastString}")
                    repository.apply {
                        setResponse(forecastString)
                        setLat(lat)
                        setLon(lon)
                    }
                }

                override fun onError(e: Throwable) {
                    viewModel.showError(e.message)
                }

                override fun onComplete() {
                }
            })
    }

    override fun setLat(lat: Float) {
        repository.setLat(lat)
    }

    override fun setLon(lon: Float) {
       repository.setLon(lon)
    }

    override fun getLat(): Float {
        return repository.getLat()
    }

    override fun getLon(): Float {
        return repository.getLon()
    }

    override fun getResponse(): Forecast? {
        val response = repository.getResponse()
        return if (response.isNotEmpty()){
            Gson().fromJson<Forecast>(response, Forecast::class.java)
        }else{
            null
        }
    }
}