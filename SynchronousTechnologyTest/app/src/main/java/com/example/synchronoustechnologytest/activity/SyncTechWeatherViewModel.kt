package com.example.synchronoustechnologytest.activity

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.synchronoustechnologytest.BuildConfig
import com.example.synchronoustechnologytest.R
import com.example.synchronoustechnologytest.model.Weather
import com.example.synchronoustechnologytest.activity.state.SyncTechWeatherState
import com.example.synchronoustechnologytest.model.Forecast
import com.example.synchronoustechnologytest.utils.NetworkUtils
import com.example.synchronoustechnologytest.workermanager.ScheduleWorker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit


class SyncTechWeatherViewModel(val context: Application) : AndroidViewModel(context),
    SyncTechWeatherContract.ViewModel, KoinComponent {

    val locationName = MutableLiveData<String>()
    val currentTemperature = MutableLiveData<String>()
    val currentHumidity = MutableLiveData<String>()
    val currentWindSpeed = MutableLiveData<String>()
    val loaderVisibility = MutableLiveData<Int>(View.VISIBLE)

    private val viewState = MutableLiveData<SyncTechWeatherState>()
    private val compositeDisposable = CompositeDisposable()

    private val presenter: SyncTechWeatherContract.Presenter

    init {
        val service = get<SyncTechWeatherContract.Service> { parametersOf(context) }
        val persistence = get<SyncTechWeatherContract.Persistence> { parametersOf(context) }
        val repository =
            get<SyncTechWeatherContract.Repository> { parametersOf(service, persistence) }
        presenter = get { parametersOf(this, repository) }
    }

    override fun getViewState(): LiveData<SyncTechWeatherState> {
        return viewState
    }

    override fun updateWeatherInfo(forecast: Forecast) {
        //TODO
        hideLoader()
    }

    override fun scheduleNextWeatherReport() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val periodicWorkRequestBuilder =
            PeriodicWorkRequestBuilder<ScheduleWorker>(2, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "ScheduleWorker",
            ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequestBuilder
        )
    }

    override fun fetchWeatherReport(lat: Float, lon: Float) {
        //first check for the wifi
        if (NetworkUtils.isWIFIConnected(context)) {
            //check weather stored lat and lon same with current lat, lon if not same, call api else use the same response
            if (lat != presenter.getLat() && lon != presenter.getLon()){
                showLoader()
                presenter.fetchCurrentWeatherForecast(lat = lat, lon = lon,
                    appId = BuildConfig.api_key)
                scheduleNextWeatherReport()
            }else{
                val forecast = presenter.getResponse()
                forecast?.let {
                    updateWeatherInfo(it)
                } ?: kotlin.run {
                    showLoader()
                    presenter.fetchCurrentWeatherForecast(
                        lat = lat, lon = lon,
                        appId = BuildConfig.api_key
                    )
                }
            }
        } else {
            showError(context.getString(R.string.network_error_msg))
        }
    }

    override fun addDispose(dispose: Disposable) {
        compositeDisposable.add(dispose)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    override fun showError(message: String?) {
        hideLoader()
        viewState.value = SyncTechWeatherState(
            showError = true,
            errorMessage = message ?: context.resources.getString(R.string.something_went_wrong)
        )
    }

    override fun updateLayLon(lat: Float, lon: Float) {

    }

    private fun showLoader() {
        loaderVisibility.value = View.VISIBLE
    }

    private fun hideLoader() {
        loaderVisibility.value = View.GONE
    }
}