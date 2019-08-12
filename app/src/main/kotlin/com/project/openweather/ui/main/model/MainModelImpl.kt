package com.project.openweather.ui.main.model

import android.location.Location
import com.project.openweather.network.api.WeatherApi
import com.project.openweather.network.dto.WeatherDto
import com.project.openweather.network.dto.WeathersDto
import io.reactivex.Single

class MainModelImpl(private val weatherApi: WeatherApi) : MainModel {

    override fun requestWeatherData(location: Location): Single<WeatherDto> =
        weatherApi.getCurrentWeatherByCoordinates(location.latitude.toString(), location.longitude.toString())

    override fun requestWeatherData(cityId: Long): Single<WeatherDto> =
        weatherApi.getCurrentWeatherById(cityId)

    override fun requestCitiesWeatherData(): Single<WeathersDto> = weatherApi.getCurrentWeatherByRectangleZone()
}