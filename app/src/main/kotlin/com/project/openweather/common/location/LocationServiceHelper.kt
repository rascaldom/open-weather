package com.project.openweather.common.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.project.openweather.common.DEFAULT_FASTEST_INTERVAL
import com.project.openweather.common.DEFAULT_LATITUDE
import com.project.openweather.common.DEFAULT_LONGITUDE
import com.project.openweather.common.DEFAULT_UPDATE_INTERVAL
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@SuppressLint("MissingPermission", "StaticFieldLeak")
object LocationServiceHelper {

    const val REQUEST_CODE_ON_LOCATION_AVAILABILITY = 9001
    const val REQUEST_CODE_CHECK_LOCATION_SETTING   = 9002

    private var applicationContext: Context? = null

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null

    private var isLocationAvailable = false

    val DEFAULT_LOCATION = Location("default").apply {
        longitude = DEFAULT_LONGITUDE
        latitude = DEFAULT_LATITUDE
    }

    private var lastLocation: Location = DEFAULT_LOCATION

    val locationSubject: PublishSubject<Location> = PublishSubject.create()
    val locationAvailableSubject: BehaviorSubject<HashMap<Int, Boolean>> = BehaviorSubject.create()

    fun initialize(context: Context) {
        applicationContext = context

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
                    locationSubject.onNext(this)
                    lastLocation = this
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                checkLocationSetting(applicationContext!!, REQUEST_CODE_ON_LOCATION_AVAILABILITY)
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

    fun isLocationAvailable() = isLocationAvailable

    fun getLastLocation(): Location {
        return lastLocation
    }

    fun checkLocationSetting(context: Context, requestCode: Int, callback: ((isLocationAvailable: Boolean) -> Unit)? = null) {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest!!)

        val pendingResult = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())
        pendingResult.addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
//                        locationAvailableSubject.onNext(true)
//                        try {
//                            (exception as ResolvableApiException).apply {
//                                startResolutionForResult(context as Activity, RequestCode)
//                            }
//                        } catch (e: IntentSender.SendIntentException) {
//                            e.printStackTrace()
//                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                    }
                }
            } finally {
                isLocationAvailable = task.isSuccessful
                locationAvailableSubject.onNext(hashMapOf(requestCode to task.isSuccessful))

                if (callback != null) {
                    callback(isLocationAvailable)
                }
            }
        }
    }
}