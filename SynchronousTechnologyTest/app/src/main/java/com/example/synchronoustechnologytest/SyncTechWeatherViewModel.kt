package com.example.synchronoustechnologytest

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import org.koin.core.component.KoinComponent

class SyncTechWeatherViewModel(context: Application): AndroidViewModel(context),
    SyncTechWeatherContract.ViewModel, KoinComponent {

    val locationName = MutableLiveData<String>()
    val currentTemperature = MutableLiveData<String>()
    val currentHumidity = MutableLiveData<String>()
    val currentWindSpeed = MutableLiveData<String>()

//    val presenter:SyncTechWeatherContract.Presenter
//
    init {

    }
}