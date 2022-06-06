package com.example.synchronoustechnologytest.activity

import android.util.Log
import androidx.work.*
import com.example.synchronoustechnologytest.model.Forecast
import com.example.synchronoustechnologytest.workermanager.ScheduleWorker
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SyncTechWeatherPresenter(
    val viewModel: SyncTechWeatherContract.ViewModel,
    val repository: SyncTechWeatherContract.Repository
) : SyncTechWeatherContract.Presenter {

    companion object{
        private const val SCHEDULER_WORKER ="ScheduleWorker"
    }

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
            .subscribe(object : Observer<Forecast> {
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
                    viewModel.updateWeatherInfo(forecast)
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
        return if (response.isNotEmpty()) {
            Gson().fromJson<Forecast>(response, Forecast::class.java)
        } else {
            null
        }
    }

    override fun getDateFromTimeStamp(timeStamp: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(timeStamp))
    }

    override fun getTimeFromTimeStamp(timeStamp: Long): String {
        val date = Date(timeStamp)
        val formatter: DateFormat = SimpleDateFormat("HH:mm:ss.SSS")
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
        return formatter.format(date)
    }

    /***
     * setup for periodic work manager to call weather api periodically
     */
    override fun scheduleNextWeatherReport() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicWorkRequestBuilder =
            PeriodicWorkRequestBuilder<ScheduleWorker>(2, TimeUnit.HOURS)
                .setConstraints(constraints).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            SCHEDULER_WORKER,
            ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequestBuilder)
    }
}