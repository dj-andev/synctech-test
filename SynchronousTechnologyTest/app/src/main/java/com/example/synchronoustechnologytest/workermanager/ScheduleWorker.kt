package com.example.synchronoustechnologytest.workermanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.synchronoustechnologytest.utils.NetworkUtils

class ScheduleWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        if (NetworkUtils.isWIFIConnected(context)) {
            Log.i("SYNC_TEST", " ScheduleWeatherCast triggered...")
            ScheduleWeatherCast(context).fetchWeatherReport()
        }
        return Result.success()
    }
}