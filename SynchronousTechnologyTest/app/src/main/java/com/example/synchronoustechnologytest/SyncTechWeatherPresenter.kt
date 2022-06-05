package com.example.synchronoustechnologytest

import android.util.Log
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
        exclude: String,
        appId: String
    ) {
        repository.fetchCurrentWeatherForecast(
            lat = lat,
            lon = lon,
            exclude = exclude,
            appId = appId
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<ResponseBody>{
                override fun onSubscribe(d: Disposable) {
                    viewModel.addDispose(d)
                }

                override fun onNext(responseBody: ResponseBody) {
                    Log.i("SYNC_TEST", "${responseBody.string()}")
                }

                override fun onError(e: Throwable) {
                    viewModel.showError(e.message)
                }

                override fun onComplete() {
                }
            })
    }
}