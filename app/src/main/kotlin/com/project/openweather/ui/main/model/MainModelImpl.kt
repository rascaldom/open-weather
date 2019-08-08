package com.project.openweather.ui.main.model

import com.project.openweather.network.api.WeatherApi
import com.project.openweather.network.dto.WeatherDto
import io.reactivex.Single

class MainModelImpl(private val weatherApi: WeatherApi) : MainModel {

    override fun requestWeatherData(lat: String, lon: String): Single<WeatherDto> = weatherApi.getCurrentPositionData(lat, lon)
}