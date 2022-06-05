package com.example.synchronoustechnologytest

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.synchronoustechnologytest.model.WeatherModel
import com.example.synchronoustechnologytest.state.SyncTechWeatherState
import com.example.synchronoustechnologytest.utils.NetworkUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

import org.koin.core.parameter.parametersOf

class SyncTechWeatherViewModel(val context: Application) : AndroidViewModel(context),
    SyncTechWeatherContract.ViewModel, KoinComponent {

    val locationName = MutableLiveData<String>()
    val currentTemperature = MutableLiveData<String>()
    val currentHumidity = MutableLiveData<String>()
    val currentWindSpeed = MutableLiveData<String>()
    val loaderVisibility = MutableLiveData<Int>(View.VISIBLE)

    val viewState = MutableLiveData<SyncTechWeatherState>()
    private val compositeDisposable = CompositeDisposable()

    private val presenter: SyncTechWeatherContract.Presenter

    //
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

    override fun updateWeatherInfo(weather: WeatherModel) {
        //TODO
        loaderVisibility.value = View.GONE
    }

    override fun scheduleNextWeatherReport() {
        //TODO
    }

    override fun fetchWeatherReport(lat: Float, lon: Float) {
        if (NetworkUtils.isNetworkConnected(context)) {
            presenter.fetchCurrentWeatherForecast(lat = lat, lon = lon,
                exclude = "hourly,daily,minutely,alerts",
                appId = BuildConfig.api_key)
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

    private fun showLoader(){
        loaderVisibility.value = View.VISIBLE
    }

    private fun hideLoader(){
        loaderVisibility.value = View.GONE
    }
}