package com.example.synchronoustechnologytest.datamanager

import com.example.synchronoustechnologytest.SyncTechWeatherContract

class SyncTechWeatherRepository(val persistence: SyncTechWeatherContract.Persistence,
                                val service: SyncTechWeatherContract.Service): SyncTechWeatherContract.Repository  {
}