package com.project.openweather.common.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import com.project.openweather.common.DEFAULT_FASTEST_INTERVAL
import com.project.openweather.common.DEFAULT_LATITUDE
import com.project.openweather.common.DEFAULT_LONGITUDE
import com.project.openweather.common.DEFAULT_UPDATE_INTERVAL
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

@SuppressLint("MissingPermission", "StaticFieldLeak")
object LocationServiceHelper {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    val DEFAULT_LOCATION = Location("default").apply {
        longitude = DEFAULT_LONGITUDE
        latitude = DEFAULT_LATITUDE
    }

    private var lastLocation: Location = DEFAULT_LOCATION

    fun initialize(context: Context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)

        setLocationRequest()
        setLocationCallback()

        requestLastLocation().observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    private fun requestLastLocation(): Single<Location> {
        return Single.create { emitter ->
            fusedLocationProviderClient!!.lastLocation
                    .addOnSuccessListener { location ->
                        location?.apply {
                            lastLocation = location
                            emitter.onSuccess(location)
                        }
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        emitter.onError(e)
                    }
        }
    }

    private fun setLocationRequest() {
        locationRequest = LocationRequest()
        with (locationRequest!!) {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = DEFAULT_UPDATE_INTERVAL.toLong()
            fastestInterval = DEFAULT_FASTEST_INTERVAL.toLong()
        }
    }

    private fun setLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                (locationResult?.lastLocation ?: DEFAULT_LOCATION).apply {
                    lastLocation = this
                }
            }
        }
    }

    fun startLocationUpdates(updateInterval: Int = DEFAULT_UPDATE_INTERVAL, fastestInterval: Int = DEFAULT_FASTEST_INTERVAL) {
        fusedLocationProviderClient?.apply {
            locationRequest!!.interval = updateInterval.toLong()
            locationRequest!!.fastestInterval = fastestInterval.toLong()
            requestLocationUpdates(locationRequest, locationCallback!!, null)
        }
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback!!)
    }

    fun getLastLocation(): Location {
        return lastLocation
    }
}