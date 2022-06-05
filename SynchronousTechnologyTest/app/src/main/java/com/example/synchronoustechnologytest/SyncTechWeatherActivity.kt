package com.example.synchronoustechnologytest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.synchronoustechnologytest.base.BaseActivity
import com.example.synchronoustechnologytest.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SyncTechWeatherActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SyncTechWeatherViewModel by viewModel { parametersOf(applicationContext)}

    override fun getLayout(): Int {
       return R.layout.activity_main
    }

    override fun initiateViews() {
        binding = viewDataBinding as ActivityMainBinding
    }

    override fun attachViewModelsWithViews() {
        binding.apply {
            lifecycleOwner = this@SyncTechWeatherActivity
            syncTechWeatherViewModel = viewModel
        }
    }

    override fun attachObserver() {
            viewModel.getViewState().observe(this@SyncTechWeatherActivity, Observer {
                it?.let {
                    when{
                        it.showError -> showSnackBar(binding.conLayout, it.errorMessage)
                    }
                }
            })
    }

    override fun getPageTitle(): Int {
        return R.string.app_name
    }

    override fun checkRunTimePermission() {
        if (ActivityCompat.checkSelfPermission(this@SyncTechWeatherActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@SyncTechWeatherActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            updateLatLonByLocationManager()
        }
    }

    private fun updateLatLonByLocationManager() {
        try {
            val location = getLastKnownLocation()
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                Log.i("SYNC_TEST","lat : $lat, lon : $lon")
            }
        } catch (e: java.lang.Exception) {
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Location? {
        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }
        return bestLocation
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(this@SyncTechWeatherActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                updateLatLonByLocationManager()
            }else{
                ActivityCompat.requestPermissions(this@SyncTechWeatherActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }
}