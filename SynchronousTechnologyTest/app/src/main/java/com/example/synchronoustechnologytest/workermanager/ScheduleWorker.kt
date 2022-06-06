package com.example.synchronoustechnologytest.workermanager

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.synchronoustechnologytest.utils.NetworkUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class ScheduleWorker(private val context: Context,
                     private val workerParameters: WorkerParameters): Worker(context, workerParameters), KoinComponent{

    private val mSharedPreference: SharedPreferences by inject { parametersOf(context) }
    override fun doWork(): Result {
        if (NetworkUtils.isWIFIConnected(context)){
            // TODO
        }
        return Result.success()
    }
}