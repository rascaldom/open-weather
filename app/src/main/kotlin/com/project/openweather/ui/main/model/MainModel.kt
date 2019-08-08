package com.project.openweather.ui.main.model

import com.project.openweather.network.dto.WeatherDto
import io.reactivex.Single

interface MainModel {

    fun requestWeatherData(lat: String, lon: String): Single<WeatherDto>

}