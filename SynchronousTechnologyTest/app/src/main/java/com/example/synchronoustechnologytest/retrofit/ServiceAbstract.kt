package com.example.synchronoustechnologytest.retrofit

import android.content.Context
import android.util.Log
import com.example.synchronoustechnologytest.R
import com.example.synchronoustechnologytest.utils.GsonException
import com.example.synchronoustechnologytest.utils.NoHostException
import com.google.gson.stream.MalformedJsonException
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import retrofit2.Call
import java.net.SocketException
import java.net.UnknownHostException

abstract class ServiceAbstract {

    protected lateinit var retrofit: APIService
    private lateinit var context: Context

    private fun getBaseUrl(): String {
        return "https://api.openweathermap.org/data/3.0/"
    }

    constructor(context: Context) {
        this.context = context
        retrofit = RetrofitClient.getInstance(getBaseUrl())
    }

    fun <T> getObservableFromCall(call: Call<T>): Observable<T> =
        Observable.create {
            try {
                val response = call.execute()
                Log.d("TEST", response.raw().request.url.toString())
                Log.d("TEST", response.body().toString())
                when {
                    response.isSuccessful -> {
                        val t = response.body()
                        t?.let { data ->
                            it.onNext(data)
                        } ?: run {
                            it.onErrorIfNotDisposed(Throwable(context.getString(R.string.something_went_wrong)))
                        }
                    }
                    else -> {
                        it.onErrorIfNotDisposed(Throwable(context.getString(R.string.something_went_wrong)))
                    }
                }
            } catch (ex: Exception) {
                if (ex is MalformedJsonException) {
                    it.onErrorIfNotDisposed(GsonException())
                } else if (ex is SocketException || ex is UnknownHostException) {
                    it.onErrorIfNotDisposed(NoHostException())
                }
            }
            it.onComplete()
        }

    private fun <T> ObservableEmitter<T>.onErrorIfNotDisposed(error: Throwable) {
        if (!this.isDisposed) {
            this.onError(error)
        }
    }
}
