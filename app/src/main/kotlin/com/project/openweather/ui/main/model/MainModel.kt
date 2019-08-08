package com.project.openweather.ui.main.model

import android.location.Location
import com.project.openweather.network.dto.WeatherDto
import io.reactivex.Single

interface MainModel {

    fun requestWeatherData(location: Location): Single<WeatherDto>

}