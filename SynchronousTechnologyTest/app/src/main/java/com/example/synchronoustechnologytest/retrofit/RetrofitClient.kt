package com.example.synchronoustechnologytest.retrofit

import com.example.synchronoustechnologytest.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class used for preparing the retrofit client, which is used for network calls
 */
class RetrofitClient {
    companion object {
        private const val TIMEOUT = 30L

        @Synchronized
        fun getInstance(url: String): APIService {
            return RetrofitClient().buildRetrofit(url).create(APIService::class.java)
        }
    }



    private fun buildRetrofit(url: String): Retrofit {
        val okHttpClient = createOKHttpClient()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOKHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }
}