package com.project.openweather.ui.main.model

import android.location.Location
import com.project.openweather.network.dto.WeatherDto
import com.project.openweather.network.dto.WeathersDto
import io.reactivex.Single

interface MainModel {

    fun requestWeatherData(location: Location): Single<WeatherDto>

    fun requestCitiesWeatherData(): Single<WeathersDto>
}