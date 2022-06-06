package com.example.synchronoustechnologytest.activity

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.synchronoustechnologytest.BuildConfig
import com.example.synchronoustechnologytest.R
import com.example.synchronoustechnologytest.activity.state.SyncTechWeatherState
import com.example.synchronoustechnologytest.model.Forecast
import com.example.synchronoustechnologytest.utils.NetworkUtils
import com.example.synchronoustechnologytest.workermanager.ScheduleWorker
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class SyncTechWeatherViewModel(val context: Application) : AndroidViewModel(context),
    SyncTechWeatherContract.ViewModel, KoinComponent {

    companion object{
        private const val SCHEDULER_WORKER ="ScheduleWorker"
    }

    val locationName = MutableLiveData<String>()
    val coordinates = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val temperature = MutableLiveData<String>()
    val humidity = MutableLiveData<String>()
    val pressure = MutableLiveData<String>()
    val windSpeed = MutableLiveData<String>()
    val sunRise = MutableLiveData<String>()
    val sunset = MutableLiveData<String>()
    val weather = MutableLiveData<String>()
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
        locationName.value = "Location - ${forecast.name}, ${forecast.sys.country}"
        coordinates.value = "Latitude - ${forecast.coord.lat}, Longitude - ${forecast.coord.lon}"
        date.value = "Date - ${getDateFromTimeStamp(forecast.dt)}"
        temperature.value = "Temperature - ${((forecast.main.temp-32)*5)/9} degree Celsius"
        humidity.value = "Humidity - ${forecast.main.humidity}"
        pressure.value = "Pressure - ${forecast.main.pressure}"
        windSpeed.value = "Wind Speed - ${forecast.wind.speed} km/h"
        sunRise.value = "Sunrise - ${getTimeFromTimeStamp(forecast.sys.sunrise)}"
        sunset.value = "Sunset - ${getTimeFromTimeStamp(forecast.sys.sunset)}"
        weather.value = "Weather - ${forecast.weather[0].main} -  ${forecast.weather[0].description}"
        hideLoader()
        scheduleNextWeatherReport()
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

    override fun fetchWeatherReport(lat: Float, lon: Float) {
        //first check for the wifi
        if (NetworkUtils.isWIFIConnected(context)) {
            //check weather stored lat and lon same with current lat, lon if not same, call api else use the same response
            if (lat != presenter.getLat() && lon != presenter.getLon()){
                presenter.setLat(lat)
                presenter.setLon(lon)
                showLoader()
                presenter.fetchCurrentWeatherForecast(lat = lat, lon = lon,
                    appId = BuildConfig.api_key)
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

    private fun showLoader() {
        loaderVisibility.value = View.VISIBLE
    }

    private fun hideLoader() {
        loaderVisibility.value = View.GONE
    }

    private fun getDateFromTimeStamp(timeStamp: Long): String{
        val formatter =  SimpleDateFormat("dd/MM/yyyy")
        return formatter.format( Date(timeStamp))
    }

    private fun getTimeFromTimeStamp(timeStamp: Long): String{
        val date = Date(timeStamp)
        val formatter: DateFormat = SimpleDateFormat("HH:mm:ss.SSS")
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
        return formatter.format(date)
    }
}