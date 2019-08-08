package com.project.openweather.ui.main.model

import android.location.Location
import com.project.openweather.network.api.WeatherApi
import com.project.openweather.network.dto.WeatherDto
import io.reactivex.Single

class MainModelImpl(private val weatherApi: WeatherApi) : MainModel {

    override fun requestWeatherData(location: Location): Single<WeatherDto> =
        weatherApi.getCurrentPositionData(location.latitude.toString(), location.longitude.toString())
}