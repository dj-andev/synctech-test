package com.example.synchronoustechnologytest.activity.di

import android.app.Application
import com.example.synchronoustechnologytest.activity.SyncTechWeatherContract
import com.example.synchronoustechnologytest.activity.SyncTechWeatherPresenter
import com.example.synchronoustechnologytest.activity.SyncTechWeatherViewModel
import com.example.synchronoustechnologytest.activity.datamanager.SyncTechWeatherPersistence
import com.example.synchronoustechnologytest.activity.datamanager.SyncTechWeatherRepository
import com.example.synchronoustechnologytest.activity.datamanager.SyncTechWeatherService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * DI for the weather module
 */
val weatherModule = module {
    viewModel { (context: Application) -> SyncTechWeatherViewModel(context = context) }

    factory<SyncTechWeatherContract.Presenter> { (viewModel: SyncTechWeatherContract.ViewModel,
                                                     repository: SyncTechWeatherContract.Repository) ->
        SyncTechWeatherPresenter(viewModel = viewModel, repository = repository)

    }
    factory<SyncTechWeatherContract.Repository> { (service: SyncTechWeatherContract.Service,
                                                      persistence: SyncTechWeatherContract.Persistence) ->
        SyncTechWeatherRepository(service = service, persistence = persistence)
    }
    factory<SyncTechWeatherContract.Service> { (context: Application) ->
        SyncTechWeatherService(
            context
        )
    }
    factory<SyncTechWeatherContract.Persistence> { (context: Application) ->
        SyncTechWeatherPersistence(
            context
        )
    }
}